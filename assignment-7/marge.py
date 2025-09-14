#!/usr/bin/env python3
import os

def main():
    # Ask for assignment number
    assignment_num = input("Enter assignment number (e.g., 7): ")
    
    root = os.path.abspath(os.path.dirname(__file__))

    # Look for TASK folders (uppercase)
    tasks = [
        d for d in os.listdir(root)
        if os.path.isdir(os.path.join(root, d)) and d.startswith("TASK")
    ]
    tasks.sort(key=lambda d: int(d.replace("TASK", "")) if d.replace("TASK", "").isdigit() else d)

    # Create output filename with assignment number
    out_path = os.path.join(
        root,
        f"Assignment{assignment_num.zfill(2)}_1000054254_MdMonjurulHasanBhuiyan.txt"
    )

    with open(out_path, "w", encoding="utf-8") as out:
        for task in tasks:
            num = task.replace("TASK", "")
            # Write folder name in lowercase
            out.write(f"//task{num}\n")

            task_dir = os.path.join(root, task)
            # Get all Java files (excluding tester files)
            java_files = sorted(
                f for f in os.listdir(task_dir)
                if f.endswith(".java") and "Tester" not in f
            )

            for fname in java_files:
                file_path = os.path.join(task_dir, fname)
                with open(file_path, "r", encoding="utf-8") as f:
                    content = f.read()
                # Only include files that DON'T have main method (design files)
                if "public static void main(String[] args)" not in content:
                    out.write(content)
                    out.write("\n")

            out.write("\n")

    print(f"✔️  Written design classes to {out_path}")

if __name__ == "__main__":
    main()
