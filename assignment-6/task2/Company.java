package task2;

public class Company {
    private String name;
    private Employee[] employees;
    private int count;

    public Company() {
        this.name = "ABC Company";
        this.employees = new Employee[3];
        this.count = 0;
    }

    public void addEmployee(Employee e) {
        if (count < employees.length) {
            employees[count] = e;
            count++;
            System.out.println(e.getName() + " has joined the company");
        } else {
            System.out.println("No more vacancy");
        }
    }

    public void removeEmployee(Employee e) {
        for (int i = 0; i < count; i++) {
            if (employees[i] == e) {
                System.out.println(e.getName() + " has left the company");
                for (int j = i; j < count - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                employees[count - 1] = null;
                count--;
                break;
            }
        }
    }

    public void details() {
        System.out.println("Company Name: " + name);
        System.out.println("Total Employee: " + count);

        System.out.println("Fulltime Employees:");
        for (int i = 0; i < count; i++) {
            if (employees[i].getType().equalsIgnoreCase("Fulltime")) {
                employees[i].details();
            }
        }

        System.out.println("Part-Time Employees:");
        for (int i = 0; i < count; i++) {
            if (employees[i].getType().equalsIgnoreCase("Part-time")) {
                employees[i].details();
            }
        }
    }
}
