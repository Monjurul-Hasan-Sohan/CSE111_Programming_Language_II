#!/usr/bin/env python3
# Windows Deep Clean & Optimize (Python)
# Save as: windows_deep_clean.py
# Run from an elevated (Administrator) PowerShell or CMD:
#   py windows_deep_clean.py --help

import os
import sys
import shutil
import argparse
import subprocess
import ctypes
import logging
import time
from datetime import datetime

# Windows-only modules
try:
    import winreg
except ImportError:
    winreg = None  # will fail later on non-Windows

LOG_DIR = os.path.join(os.path.expanduser("~"), "WindowsDeepCleanLogs")
os.makedirs(LOG_DIR, exist_ok=True)
LOG_PATH = os.path.join(LOG_DIR, f"deep_clean_{datetime.now().strftime('%Y%m%d_%H%M%S')}.log")

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s | %(levelname)-8s | %(message)s",
    handlers=[logging.FileHandler(LOG_PATH, encoding="utf-8"), logging.StreamHandler(sys.stdout)],
)
log = logging.getLogger("DeepClean")

def is_windows():
    return os.name == "nt"

def is_admin():
    try:
        return ctypes.windll.shell32.IsUserAnAdmin()
    except Exception:
        return False

def run(cmd, check=False, shell=False):
    log.info(f"$ {' '.join(cmd) if isinstance(cmd, list) else cmd}")
    try:
        return subprocess.run(cmd, check=check, shell=shell, capture_output=True, text=True)
    except Exception as e:
        log.warning(f"Command failed: {e}")
        return None

def safe_remove(path, dry_run=False):
    if not path or not os.path.exists(path):
        return 0, 0
    total_files = 0
    total_dirs = 0
    if os.path.isfile(path):
        total_files = 1
        if not dry_run:
            try:
                os.remove(path)
            except Exception as e:
                log.debug(f"Failed to remove file {path}: {e}")
        return total_files, total_dirs

    # Directory
    for root, dirs, files in os.walk(path, topdown=False):
        for f in files:
            fp = os.path.join(root, f)
            total_files += 1
            if not dry_run:
                try:
                    os.chmod(fp, 0o700)
                    os.remove(fp)
                except Exception as e:
                    log.debug(f"Failed to remove file {fp}: {e}")
        for d in dirs:
            dp = os.path.join(root, d)
            total_dirs += 1
            if not dry_run:
                try:
                    os.chmod(dp, 0o700)
                    os.rmdir(dp)
                except Exception as e:
                    log.debug(f"Failed to remove dir {dp}: {e}")
    # remove the root directory contents only, not the root itself
    return total_files, total_dirs

def empty_recycle_bin():
    # SHEmptyRecycleBinW(NULL, NULL, NOCONFIRMATION|NOPROGRESSUI|NOSOUND)
    SHERB_NOCONFIRMATION = 0x00000001
    SHERB_NOPROGRESSUI  = 0x00000002
    SHERB_NOSOUND       = 0x00000004
    try:
        ctypes.windll.shell32.SHEmptyRecycleBinW(None, None,
            SHERB_NOCONFIRMATION | SHERB_NOPROGRESSUI | SHERB_NOSOUND)
        log.info("Recycle Bin emptied.")
    except Exception as e:
        log.warning(f"Could not empty Recycle Bin: {e}")

def stop_services(services):
    for s in services:
        run(["sc", "stop", s])

def start_services(services):
    for s in services:
        run(["sc", "start", s])

def clean_temp_and_cache(dry_run=False, include_prefetch=True, include_wu_cache=True):
    files_deleted = dirs_deleted = 0

    # User TEMP and Windows TEMP
    temp_env = os.environ.get("TEMP") or os.environ.get("TMP")
    win_temp = os.path.join(os.environ.get("SystemRoot", r"C:\Windows"), "Temp")
    for p in [temp_env, win_temp]:
        if p and os.path.exists(p):
            log.info(f"Cleaning temp: {p}")
            f, d = safe_remove(p, dry_run)
            files_deleted += f; dirs_deleted += d

    # Prefetch
    if include_prefetch:
        prefetch = os.path.join(os.environ.get("SystemRoot", r"C:\Windows"), "Prefetch")
        if os.path.exists(prefetch):
            log.info(f"Cleaning Prefetch: {prefetch}")
            f, d = safe_remove(prefetch, dry_run)
            files_deleted += f; dirs_deleted += d

    # Windows Update cache
    if include_wu_cache:
        wu = os.path.join(os.environ.get("SystemRoot", r"C:\Windows"),
                          "SoftwareDistribution", "Download")
        if os.path.exists(wu):
            log.info("Stopping Windows Update services...")
            stop_services(["wuauserv", "bits"])
            log.info(f"Cleaning Windows Update cache: {wu}")
            f, d = safe_remove(wu, dry_run)
            files_deleted += f; dirs_deleted += d
            log.info("Starting Windows Update services...")
            start_services(["wuauserv", "bits"])

    log.info(f"Temp/Cache cleaned: files={files_deleted}, dirs={dirs_deleted}")

def enumerate_run_values(root_hive, key_path):
    values = []
    try:
        k = winreg.OpenKey(root_hive, key_path, 0, winreg.KEY_READ)
    except FileNotFoundError:
        return values
    idx = 0
    while True:
        try:
            name, data, vtype = winreg.EnumValue(k, idx)
            values.append((name, data, vtype))
            idx += 1
        except OSError:
            break
    winreg.CloseKey(k)
    return values

def delete_run_value(root_hive, key_path, name):
    try:
        k = winreg.OpenKey(root_hive, key_path, 0, winreg.KEY_SET_VALUE)
        winreg.DeleteValue(k, name)
        winreg.CloseKey(k)
        return True
    except Exception:
        return False

def manage_startup(disable=False, backup_dir=None, dry_run=False):
    if winreg is None:
        log.warning("winreg not available. Skipping startup management.")
        return

    hives = [(winreg.HKEY_CURRENT_USER, r"Software\Microsoft\Windows\CurrentVersion\Run"),
             (winreg.HKEY_LOCAL_MACHINE, r"Software\Microsoft\Windows\CurrentVersion\Run")]

    # List values
    all_values = []
    for hive, path in hives:
        vals = enumerate_run_values(hive, path)
        for n, d, t in vals:
            all_values.append((hive, path, n, d))
            log.info(f"Startup (Run): {('HKCU' if hive==winreg.HKEY_CURRENT_USER else 'HKLM')}\\{path} -> {n} = {d}")

    # Startup folders
    startup_user = os.path.join(os.environ.get("APPDATA", ""), r"Microsoft\Windows\Start Menu\Programs\Startup")
    startup_all  = os.path.join(os.environ.get("ProgramData", ""), r"Microsoft\Windows\Start Menu\Programs\Startup")
    for folder in [startup_user, startup_all]:
        if folder and os.path.isdir(folder):
            for entry in os.listdir(folder):
                log.info(f"Startup (Folder): {os.path.join(folder, entry)}")

    if not disable:
        log.info("Startup items listed only (no changes). Use --disable-startup to remove them safely.")
        return

    # Prepare backup folder for Startup shortcuts/scripts
    if backup_dir:
        os.makedirs(backup_dir, exist_ok=True)

    # Remove Run values (conservative: remove everything not in allowlist)
    allowlist = {
        "SecurityHealth", "Windows Security", "WindowsDefender", "OneDrive",
        "RtkAudUService64", "NvBackend", "NVIDIA GeForce Experience",
    }
    removed = 0
    for hive, path, name, data in all_values:
        if name in allowlist:
            continue
        if dry_run:
            log.info(f"[DRY-RUN] Would remove Run value: {name}")
            removed += 1
        else:
            if delete_run_value(hive, path, name):
                log.info(f"Removed Run value: {name}")
                removed += 1

    # Move (not delete) startup folder items to backup
    moved = 0
    for folder in [startup_user, startup_all]:
        if folder and os.path.isdir(folder):
            for entry in os.listdir(folder):
                src = os.path.join(folder, entry)
                if backup_dir:
                    dst = os.path.join(backup_dir, f"{int(time.time())}_{entry}")
                else:
                    dst = src + ".disabled"
                try:
                    if dry_run:
                        log.info(f"[DRY-RUN] Would move {src} -> {dst}")
                    else:
                        shutil.move(src, dst)
                    moved += 1
                    log.info(f"Disabled startup item: {src}")
                except Exception as e:
                    log.debug(f"Could not move {src}: {e}")

    log.info(f"Startup disabled: run_values_removed={removed}, folder_items_moved={moved}")

def system_health(run_sfc=True, run_dism=True):
    if run_sfc:
        log.info("Running SFC (this may take a while)...")
        run(["sfc", "/scannow"])
    if run_dism:
        log.info("Running DISM (this may take a while)...")
        run(["DISM", "/Online", "/Cleanup-Image", "/RestoreHealth"])

def optimize_drives():
    # /C = all drives, /O = optimize, SSDs get TRIM, HDDs get defrag
    log.info("Optimizing drives with defrag /C /O ...")
    run(["defrag.exe", "/C", "/O"])

def query_trim_status():
    res = run(["fsutil", "behavior", "query", "DisableDeleteNotify"])
    if res and res.stdout:
        log.info(res.stdout.strip())

def main():
    if not is_windows():
        print("This script is for Windows only.")
        sys.exit(1)
    parser = argparse.ArgumentParser(
        description="Deep clean and optimize a Windows installation. Run from an elevated console."
    )
    parser.add_argument("--dry-run", action="store_true", help="Show what would change without deleting/moving.")
    parser.add_argument("--no-prefetch", action="store_true", help="Skip Prefetch cleanup.")
    parser.add_argument("--no-wu-cache", action="store_true", help="Skip Windows Update cache cleanup.")
    parser.add_argument("--list-startup", action="store_true", help="List startup items and exit.")
    parser.add_argument("--disable-startup", action="store_true", help="Disable startup items (Run keys + Startup folders).")
    parser.add_argument("--startup-backup", default=os.path.join(LOG_DIR, "startup_backup"),
                        help="Folder to move Startup entries into when disabling (default: log dir).")
    parser.add_argument("--no-sfc", action="store_true", help="Skip SFC system file check.")
    parser.add_argument("--no-dism", action="store_true", help="Skip DISM health restore.")
    parser.add_argument("--no-optimize", action="store_true", help="Skip drive optimization.")
    parser.add_argument("--no-empty-recyclebin", action="store_true", help="Skip emptying Recycle Bin.")
    args = parser.parse_args()

    log.info("=== Windows Deep Clean & Optimize (Python) ===")
    log.info(f"Log file: {LOG_PATH}")
    if not is_admin():
        log.warning("Not running as Administrator. Some steps may fail. Right-click PowerShell/CMD and 'Run as administrator' for best results.")

    if args.list_startup:
        manage_startup(disable=False, dry_run=True)
        log.info("Startup listing complete.")
        sys.exit(0)

    # Clean temp/cache
    clean_temp_and_cache(
        dry_run=args.dry_run,
        include_prefetch=(not args.no_prefetch),
        include_wu_cache=(not args.no_wu_cache),
    )

    # Startup control
    if args.disable_startup:
        manage_startup(disable=True, backup_dir=args.startup_backup, dry_run=args.dry_run)
    else:
        log.info("Startup not changed. Use --disable-startup to turn off auto-launchers (a backup is made).")

    # Health checks
    system_health(run_sfc=(not args.no_sfc), run_dism=(not args.no_dism))

    # Optimize
    if not args.no_optimize:
        optimize_drives()
        query_trim_status()

    # Empty recycle bin
    if not args.no_empty_recyclebin:
        empty_recycle_bin()

    log.info("=== Completed. Reboot recommended. ===")

if __name__ == "__main__":
    main()
