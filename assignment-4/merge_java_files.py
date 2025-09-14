#!/usr/bin/env python3
import os

def main():
    # root directory (where this script lives)
    root = os.path.abspath(os.path.dirname(__file__))

    # collect all "taskN" folders and sort by N
    tasks = [
        d for d in os.listdir(root)
        if os.path.isdir(os.path.join(root, d)) and d.startswith("task")
    ]
    tasks.sort(key=lambda d: int(d.replace("task", "")) if d.replace("task", "").isdigit() else d)

    # output filename as requested
    out_path = os.path.join(
        root,
        "Assignment04_1000054254_MdMonjurulHasanBhuiyan.txt"
    )

    with open(out_path, "w", encoding="utf-8") as out:
        for task in tasks:
            num = task.replace("task", "")
            # only comment is //taskN
            out.write(f"//task{num}\n")

            task_dir = os.path.join(root, task)
            # pick up only non-Tester .java files
            design_files = sorted(
                f for f in os.listdir(task_dir)
                if f.endswith(".java") and "Tester" not in f
            )

            for fname in design_files:
                file_path = os.path.join(task_dir, fname)
                with open(file_path, "r", encoding="utf-8") as f:
                    content = f.read()
                # skip any class containing a main method
                if "public static void main(String[] args)" in content:
                    continue
                # dump the file verbatim
                out.write(content)
                out.write("\n")

            # blank line before next task
            out.write("\n")

    print(f"✔️  Written design classes to {out_path}")

if __name__ == "__main__":
    main()
