package task4;
public class Employee {
    String name;
    double salary;
    String designation;

    public void newEmployee(String name) {
        this.name = name;
        this.salary = 30000.0;
        this.designation = "junior";
    }

    public void displayInfo() {
        System.out.println("Employee Name: " + name);
        System.out.println("Employee Salary: " + salary + " Tk");
        System.out.println("Employee Designation: " + designation);
    }

    public void calculateTax() {
        if (salary > 50000) {
            double tax = salary * 0.30;
            System.out.println(name + " Tax Amount: " + tax + " Tk");
        } else if (salary > 30000) {
            double tax = salary * 0.10;
            System.out.println(name + " Tax Amount: " + tax + " Tk");
        } else {
            System.out.println("No need to pay tax");
        }
    }

    public void promoteEmployee(String newRole) {
        if (newRole.equals("senior")) {
            salary += 25000;
        } else if (newRole.equals("lead")) {
            salary += 50000;
        } else if (newRole.equals("manager")) {
            salary += 75000;
        }
        designation = newRole;
        System.out.println(name + " has been promoted to " + newRole);
        System.out.println("New Salary: " + salary + " Tk");
    }
}
