# This line imports the 'os' module, which contains functions for working with the operating system
# (like creating folders, files, etc.)
import os

def create_task_folders():
    """
    This is a function definition. Functions are like mini-programs that do a specific task.
    This function will create 4 folders named TASK1, TASK2, TASK3, and TASK4.
    The text between triple quotes is called a "docstring" - it explains what the function does.
    """
    
    # This is a for loop. It will repeat the code inside it multiple times.
    # range(1, 5) creates a sequence: 1, 2, 3, 4 (note: it stops before 5)
    # So 'i' will take the values 1, 2, 3, and 4 in each iteration
    for i in range(1, 5):
        # f"TASK{i}" is called an f-string (formatted string). 
        # It creates a string where {i} gets replaced with the current value of i
        # So when i=1, folder_name becomes "TASK1"
        # When i=2, folder_name becomes "TASK2", and so on
        folder_name = f"TASK{i}"
        
        # try-except is used for error handling
        # If something goes wrong in the 'try' block, the code jumps to 'except'
        try:
            # os.makedirs() is a function that creates a folder (directory)
            # folder_name is the name of the folder we want to create
            # exist_ok=True means "don't show an error if the folder already exists"
            os.makedirs(folder_name, exist_ok=True)
            
            # print() displays text on the screen
            # This will show which folder was created successfully
            print(f"Created folder: {folder_name}")
            
        except Exception as e:
            # This runs only if there was an error in the try block
            # 'e' contains information about what went wrong
            print(f"Error creating folder {folder_name}: {e}")

# This is a special Python condition that checks if this file is being run directly
# (not imported from another file). It's a common Python pattern.
if __name__ == "__main__":
    # These print statements will show when the program starts and finishes
    print("Creating task folders...")
    
    # This calls our function to actually create the folders
    create_task_folders()
    
    print("Task folders creation completed!")
