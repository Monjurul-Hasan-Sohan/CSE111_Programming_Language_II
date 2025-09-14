class Employee {
    public String name;
    private double baseSalary;
    private int hoursWorked;

    public Employee(String name, double baseSalary, int hoursWorked){
        this.name = name;
        this.baseSalary = baseSalary;
        this.hoursWorked = hoursWorked;
    }
    public double getBaseSalary() 
    {
         return baseSalary; 
    }
    public void setBaseSalary(double baseSalary)
    {
        this.baseSalary = baseSalary;
    }
    public int getHoursWorked() 
    {
        return hoursWorked;
    }
    public void setHoursWorked(int hoursWorked)
    {
        this.hoursWorked = hoursWorked;
    }

    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Base Salary: $" + baseSalary);
        System.out.println("Work Hours: " + hoursWorked);
    }
}

class Manager extends Employee {
    private double bonusPercent;
    private double finalSalary;

    public Manager(String name, double baseSalary, int hoursWorked, double bonusPercent){
        super(name, baseSalary, hoursWorked);
        this.bonusPercent = bonusPercent;
    }

    public void calculateSalary() {
        double base = getBaseSalary();
        if (getHoursWorked() > 40) {
            finalSalary = base + (base * bonusPercent / 100.0);
        } else {
            finalSalary = base;
        }
    }

    public void requestIncrement(double amount) {
        if (getHoursWorked() > 100) {
            setBaseSalary(getBaseSalary() + amount);
            System.out.println("$" + (int)amount + " Increment approved.");
        } else if (getHoursWorked() > 80) {
            double half = amount / 2.0;
            setBaseSalary(getBaseSalary() + half);
            System.out.println("$" + (int)half + " Increment approved.");
        } else {
            System.out.println("Increment denied.");
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Bonus: " + bonusPercent + " %");
        System.out.println("Final Salary: $" + finalSalary);
    }
}

class Developer extends Employee {
    private String language;
    private double finalSalary;

    public Developer(String name, double baseSalary, int hoursWorked, String language){
        super(name, baseSalary, hoursWorked);
        this.language = language;
    }

    public void calculateSalary() {
        finalSalary = getBaseSalary();
        if ("Java".equals(language)) {
            finalSalary += 700;
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Language: " + language);
        System.out.println("Final Salary: $" + finalSalary);
    }
}

public class EmployeeTester {
    public static void main(String[] args) {
        Manager neymar = new Manager("Neymar", 1000, 45, 10);
        Developer messi = new Developer("Messi", 1000, 50, "Java");
        Developer chiesa = new Developer("Chiesa", 1000, 50, "Javascript");

        neymar.calculateSalary();
        System.out.println("1.=========");
        neymar.displayInfo();

        System.out.println("2.=========");
        neymar.requestIncrement(100);

        System.out.println("3.=========");
        neymar.setHoursWorked(85);
        neymar.requestIncrement(100);

        System.out.println("4.=========");
        neymar.calculateSalary();

        System.out.println("5.=========");
        neymar.displayInfo();

        System.out.println("6.=========");
        messi.calculateSalary();

        System.out.println("7.=========");
        messi.displayInfo();

        System.out.println("8.=========");
        chiesa.calculateSalary();

        System.out.println("9.=========");
        chiesa.displayInfo();
    }
}
