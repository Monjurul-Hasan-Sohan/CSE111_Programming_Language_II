import java.util.Arrays;

class Student {
    String name;
    int age;

    // Constructor
    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Override toString to print the object nicely
    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}

public class StudentArrayDemo {
    public static void main(String[] args) {
        // Create an array to hold 3 Student objects
        Student[] students = new Student[3];

        // Initialize each element with a new Student object
        students[0] = new Student("Alice", 20);
        students[1] = new Student("Bob", 22);
        students[2] = new Student("Charlie", 19);

        // Print all student objects using Arrays.toString()
        System.out.println("All students:");
        System.out.println(Arrays.toString(students));
    }
}
