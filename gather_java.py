#!/usr/bin/env python3
import re
from pathlib import Path
from datetime import datetime

DEFAULT_ID = "1000054254"
DEFAULT_NAME = "MdMonjurulHasanBhuiyan"

# Regex to detect the start of any main method signature (tolerates newlines/whitespace)
MAIN_SIG = re.compile(r'public\s+static\s+void\s+main\s*\(', re.IGNORECASE | re.MULTILINE)

def read_text_any(p: Path) -> str:
    for enc in ("utf-8", "cp1252", "latin-1"):
        try:
            return p.read_text(encoding=enc)
        except Exception:
            continue
    return p.read_bytes().decode("utf-8", errors="replace")

def remove_all_main_methods(src: str) -> str:
    """
    Remove every 'public static void main(...) { ... }' method from Java source.
    - Handles String[] args, String args[], String ... args, any whitespace/newline
    - Handles comments // and /* ... */, and strings '...' / "..."
    - Leaves the rest of the file untouched (including other classes/methods)
    """
    n = len(src)
    i = 0
    parts = []

    def skip_string(j: int, quote: str) -> int:
        j += 1
        while j < n:
            if src[j] == quote and src[j - 1] != '\\':
                return j + 1
            j += 1
        return j

    def skip_line_comment(j: int) -> int:
        while j < n and src[j] not in "\r\n":
            j += 1
        return j

    def skip_block_comment(j: int) -> int:
        j += 2  # already at /*
        while j < n - 1:
            if src[j] == '*' and src[j + 1] == '/':
                return j + 2
            j += 1
        return n

    while True:
        m = MAIN_SIG.search(src, i)
        if not m:
            parts.append(src[i:])
            break

        start = m.start()          # start of 'public'
        j = m.end()                # right after '('

        # Walk to the matching ')' of main signature
        paren = 1
        while j < n and paren > 0:
            if src.startswith('//', j):
                j = skip_line_comment(j + 2)
                continue
            if src.startswith('/*', j):
                j = skip_block_comment(j)
                continue

            c = src[j]
            if c in ('"', "'"):
                j = skip_string(j, c)
                continue
            if c == '(':
                paren += 1
            elif c == ')':
                paren -= 1
            j += 1

        # From here, scan forward to the opening '{' of the method body
        while j < n:
            if src.startswith('//', j):
                j = skip_line_comment(j + 2)
                continue
            if src.startswith('/*', j):
                j = skip_block_comment(j)
                continue

            c = src[j]
            if c in ('"', "'"):
                j = skip_string(j, c)
                continue
            if c == '{':
                break
            j += 1

        if j >= n or src[j] != '{':
            # If no body found, just give up and keep text
            parts.append(src[i:])
            break

        # Now skip the whole method body by brace depth
        j += 1
        depth = 1
        while j < n and depth > 0:
            if src.startswith('//', j):
                j = skip_line_comment(j + 2)
                continue
            if src.startswith('/*', j):
                j = skip_block_comment(j)
                continue

            c = src[j]
            if c in ('"', "'"):
                j = skip_string(j, c)
                continue
            if c == '{':
                depth += 1
            elif c == '}':
                depth -= 1
            j += 1

        # Keep everything before the main method, drop the method, continue
        parts.append(src[i:start])
        i = j  # continue scanning after the removed main
    return ''.join(parts)

def zero_pad(n: int) -> str:
    return f"{n:02d}"

def find_task_dirs(base: Path) -> list[Path]:
    # Direct subfolders whose name starts with 'task' (any case), sorted by numeric suffix
    cands = [p for p in base.iterdir() if p.is_dir() and p.name.lower().startswith("task")]
    def k(p: Path):
        m = re.search(r'(\d+)', p.name)
        return (int(m.group(1)) if m else 10**9, p.name.lower())
    return sorted(cands, key=k)

def main():
    print("Press Enter to accept defaults shown in [brackets].\n")
    name = input(f"Your Name [{DEFAULT_NAME}]: ").strip() or DEFAULT_NAME
    sid  = input(f"Student ID [{DEFAULT_ID}]: ").strip() or DEFAULT_ID
    raw  = input("Assignment number (e.g., 1): ").strip()
    try:
        anum = int(raw) if raw else 1
    except ValueError:
        print("Invalid number; defaulting to 1.")
        anum = 1

    safe_name = re.sub(r'\s+', '', name)
    out_file = f"Assignment {zero_pad(anum)}_{sid}_{safe_name}.txt"
    out_path = Path.cwd() / out_file

    base = Path.cwd()
    tasks = find_task_dirs(base)
    if not tasks:
        print("No task folders found (expected TASK1, Task2, ...).")
        return

    lines = []
    # Optional header (keep it brief)
    lines += [
        f"// Student ID: {sid}",
        f"// Name: {name}",
        f"// Assignment: {zero_pad(anum)}",
        f"// Generated: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}",
        ""
    ]

    for tdir in tasks:
        m = re.search(r'(\d+)', tdir.name)
        task_label = f"Task {m.group(1)}" if m else tdir.name
        lines.append(f"//{task_label}")

        java_files = sorted(tdir.rglob("*.java"))
        if not java_files:
            lines.append("// (No .java files found)")
            lines.append("")
            continue

        for jf in java_files:
            src = read_text_any(jf)
            stripped = remove_all_main_methods(src).rstrip()

            # If there are multiple files in a task, show which file segment is which
            if len(java_files) > 1:
                rel = jf.relative_to(base)
                lines.append(f"// File: {rel}")

            lines.append(stripped if stripped else "// (File contained only main method)")
            lines.append("")  # blank line after each file

        lines.append("")  # extra gap after each task

    out_path.write_text("\n".join(lines), encoding="utf-8")
    print(f"Done! Wrote: {out_path}")

if __name__ == "__main__":
    main()
