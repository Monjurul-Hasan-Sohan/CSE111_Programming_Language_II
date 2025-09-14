#!/usr/bin/env python3
import os

def main():
    root = os.path.abspath(os.path.dirname(__file__))

    tasks = [
        d for d in os.listdir(root)
        if os.path.isdir(os.path.join(root, d)) and d.startswith("task")
    ]
    tasks.sort(key=lambda d: int(d.replace("task", "")) if d.replace("task", "").isdigit() else d)

    out_path = os.path.join(
        root,
        "Assignment06_1000054254_MdMonjurulHasanBhuiyan.txt"
    )

    with open(out_path, "w", encoding="utf-8") as out:
        for task in tasks:
            num = task.replace("task", "")
            out.write(f"//task{num}\n")

            task_dir = os.path.join(root, task)
            design_files = sorted(
                f for f in os.listdir(task_dir)
                if f.endswith(".java") and "Tester" not in f
            )

            for fname in design_files:
                file_path = os.path.join(task_dir, fname)
                with open(file_path, "r", encoding="utf-8") as f:
                    content = f.read()
                if "public static void main(String[] args)" in content:
                    continue
                out.write(content)
                out.write("\n")

            out.write("\n")

    print(f"✔️  Written design classes to {out_path}")

if __name__ == "__main__":
    main()
