#!/usr/bin/env python3
import re
from pathlib import Path

DEFAULT_ID = "1000054254"
DEFAULT_NAME = "MdMonjurulHasanBhuiyan"

MAIN_SIG = re.compile(r'public\s+static\s+void\s+main\s*\(', re.IGNORECASE | re.MULTILINE)

def read_text_any(p: Path) -> str:
    for enc in ("utf-8", "cp1252", "latin-1"):
        try:
            return p.read_text(encoding=enc)
        except Exception:
            continue
    return p.read_bytes().decode("utf-8", errors="replace")

# ---- scanning helpers (strings/comments safe) ----
def _skip_string(s: str, j: int, quote: str) -> int:
    n = len(s); j += 1
    while j < n:
        if s[j] == quote and s[j - 1] != '\\':
            return j + 1
        j += 1
    return j

def _skip_line_comment(s: str, j: int) -> int:
    n = len(s)
    while j < n and s[j] not in "\r\n":
        j += 1
    return j

def _skip_block_comment(s: str, j: int) -> int:
    n = len(s); j += 2  # already at /*
    while j < n - 1:
        if s[j] == '*' and s[j + 1] == '/':
            return j + 2
        j += 1
    return n
# --------------------------------------------------

def _remove_all_main_methods(src: str) -> str:
    """Strip every main(...) method body; used internally."""
    n = len(src); i = 0; out = []
    while True:
        m = MAIN_SIG.search(src, i)
        if not m:
            out.append(src[i:]); break
        start = m.start(); j = m.end()  # after '('

        # match ')' of signature
        paren = 1
        while j < n and paren > 0:
            if src.startswith('//', j):
                j = _skip_line_comment(src, j + 2); continue
            if src.startswith('/*', j):
                j = _skip_block_comment(src, j); continue
            c = src[j]
            if c in ('"', "'"):
                j = _skip_string(src, j, c)
            elif c == '(':
                paren += 1; j += 1
            elif c == ')':
                paren -= 1; j += 1
            else:
                j += 1

        # find body '{'
        while j < n:
            if src.startswith('//', j):
                j = _skip_line_comment(src, j + 2); continue
            if src.startswith('/*', j):
                j = _skip_block_comment(src, j); continue
            c = src[j]
            if c in ('"', "'"):
                j = _skip_string(src, j, c); continue
            if c == '{':
                break
            j += 1
        if j >= n or src[j] != '{':
            out.append(src[i:]); break  # malformed

        # skip body by brace depth
        j += 1; depth = 1
        while j < n and depth > 0:
            if src.startswith('//', j):
                j = _skip_line_comment(src, j + 2); continue
            if src.startswith('/*', j):
                j = _skip_block_comment(src, j); continue
            c = src[j]
            if c in ('"', "'"):
                j = _skip_string(src, j, c)
            elif c == '{':
                depth += 1; j += 1
            elif c == '}':
                depth -= 1; j += 1
            else:
                j += 1

        out.append(src[i:start])  # drop the method
        i = j
    return ''.join(out)

def _line_start(s: str, i: int) -> int:
    """Index of first char after the last newline before i (start of that line)."""
    j = s.rfind('\n', 0, i)
    if j == -1:
        return 0
    return j + 1

def _find_class_ranges(src: str):
    """
    Return (start,end) index pairs for each class block.
    Start is set to the **start of the line** containing 'class' so modifiers
    like 'public', 'final', etc. are removed too.
    """
    n = len(src); i = 0; ranges = []
    while i < n:
        # skip comments/strings
        if src.startswith('//', i):
            i = _skip_line_comment(src, i + 2); continue
        if src.startswith('/*', i):
            i = _skip_block_comment(src, i); continue
        c = src[i]
        if c in ('"', "'"):
            i = _skip_string(src, i, c); continue

        # detect 'class' at word boundary
        if src.startswith('class', i) and (i == 0 or not (src[i-1].isalnum() or src[i-1] == '_')) \
           and (i + 5 <= n and (i + 5 == n or not (src[i+5].isalnum() or src[i+5] == '_'))):
            # back up to the start of the line to include modifiers like 'public'
            cls_start = _line_start(src, i)

            # find opening '{' of class
            j = i + 5
            while j < n:
                if src.startswith('//', j):
                    j = _skip_line_comment(src, j + 2); continue
                if src.startswith('/*', j):
                    j = _skip_block_comment(src, j); continue
                d = src[j]
                if d in ('"', "'"):
                    j = _skip_string(src, j, d); continue
                if d == '{':
                    break
                j += 1
            if j >= n or src[j] != '{':
                i += 5; continue  # malformed

            # scan to matching '}'
            j += 1; depth = 1
            while j < n and depth > 0:
                if src.startswith('//', j):
                    j = _skip_line_comment(src, j + 2); continue
                if src.startswith('/*', j):
                    j = _skip_block_comment(src, j); continue
                d = src[j]
                if d in ('"', "'"):
                    j = _skip_string(src, j, d)
                elif d == '{':
                    depth += 1; j += 1
                elif d == '}':
                    depth -= 1; j += 1
                else:
                    j += 1
            cls_end = j
            ranges.append((cls_start, cls_end))
            i = j
            continue

        i += 1
    return ranges

def drop_classes_with_main(src: str) -> str:
    """Remove entire class blocks that contain a real main() method."""
    ranges = _find_class_ranges(src)
    if not ranges:
        return _remove_all_main_methods(src)

    to_drop = []
    for (s, e) in ranges:
        sub = src[s:e]
        if _remove_all_main_methods(sub) != sub:  # main was present
            to_drop.append((s, e))

    if not to_drop:
        return src

    out = []
    last = 0
    for (s, e) in sorted(to_drop):
        if s > last:
            out.append(src[last:s])
        last = max(last, e)
    out.append(src[last:])
    return ''.join(out)

def zero_pad(n: int) -> str:
    return f"{n:02d}"

def find_task_dirs(base: Path) -> list[Path]:
    cands = [p for p in base.iterdir() if p.is_dir() and p.name.lower().startswith("task")]
    def key(p: Path):
        m = re.search(r'(\d+)', p.name)
        return (int(m.group(1)) if m else 10**9, p.name.lower())
    return sorted(cands, key=key)

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

    keep_main_ans = input(
        "Do you want to keep the main method(s) (public static void main(...))? [y/N]: "
    ).strip().lower()
    keep_main = keep_main_ans in ("y", "yes")

    safe_name = re.sub(r'\s+', '', name)
    out_file = f"Assignment {zero_pad(anum)}_{sid}_{safe_name}.txt"
    out_path = Path.cwd() / out_file

    base = Path.cwd()
    tasks = find_task_dirs(base)
    if not tasks:
        print("No task folders found (expected TASK1, Task2, ...).")
        return

    lines = []
    for tdir in tasks:
        m = re.search(r'(\d+)', tdir.name)
        task_label = f"//Task {m.group(1)}" if m else f"//{tdir.name}"
        lines.append(task_label)

        java_files = sorted(tdir.rglob("*.java"))
        if not java_files:
            lines.append("")
            continue

        for jf in java_files:
            src = read_text_any(jf)
            out_src = src if keep_main else drop_classes_with_main(src)
            out_src = out_src.rstrip()

            if len(java_files) > 1:
                rel = jf.relative_to(base)
                lines.append(f"// File: {rel}")

            lines.append(out_src if out_src else "// (File contained only a main-class)")
            lines.append("")
        lines.append("")

    out_path.write_text("\n".join(lines), encoding="utf-8")
    print(f"Done! Wrote: {out_path}")

if __name__ == "__main__":
    main()
