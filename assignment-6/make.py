import os

task3 = {
"Student.java": """package task3;

public class Student {
  private String name;
  private int id;
  private double cgpa;

  public Student(String n, int i, double c) {
    name = n;
    id = i;
    cgpa = c;
  }

  public String getName() { return name; }
  public int getId() { return id; }
  public double getCgpa() { return cgpa; }
  public void setId(int i) { id = i; }
}
""",
"Department.java": """package task3;

public class Department {
  private String name;
  private Student[] list = new Student[5];
  private int count = 0;

  public Department(String n) {
    name = n;
  }

  public void addStudent(Student a, Student b, Student c) {
    addStudent(a);
    addStudent(b);
    addStudent(c);
  }

  public void addStudent(Student s) {
    if (s == null) return;
    int i;
    for (i = 0; i < count; i++) {
      if (list[i].getId() == s.getId()) {
        System.out.println("Student with the same ID already exists, Please try with another ID");
        return;
      }
    }
    if (count >= list.length) return;
    list[count] = s;
    count++;
    System.out.println("Welcome to " + name + " department, " + s.getName());
  }

  public void findStudent(int id) {
    if (id <= 0) {
      System.out.println("Student with this ID doesn't exist, Please give a valid ID");
      return;
    }
    int i;
    for (i = 0; i < count; i++) {
      if (list[i].getId() == id) {
        System.out.println("Student info:");
        System.out.println("Student Name: " + list[i].getName());
        System.out.println("ID: " + list[i].getId());
        System.out.println("CGPA: " + list[i].getCgpa());
        return;
      }
    }
    System.out.println("Student with this ID doesn't exist, Please give a valid ID");
  }

  public void details() {
    System.out.println("Department Name: " + name);
    System.out.println("Number of student:" + count);
    System.out.println("Details of the students:");
    int i;
    for (i = 0; i < count; i++) {
      System.out.println("Student name: " + list[i].getName() + ", ID: " + list[i].getId() + ", cgpa: " + list[i].getCgpa());
    }
  }
}
""",
"Tester1.java": """package task3;

public class Tester1{
  public static void main(String[] args) {
    Student s1 = new Student("Akib", 10, 3.29);
    Student s2 = new Student("Reza", 15, 3.45);
    Student s3 = new Student("Kabir", 20, 4.0);
    System.out.println("1=============");
    Department cse = new Department("CSE");
    cse.findStudent(-100);
    System.out.println("2=============");
    cse.addStudent(s1, s2, s3);
    System.out.println("3=============");
    cse.details();
    System.out.println("4=============");
    cse.findStudent(15);
    System.out.println("5=============");
    Student s4 = new Student("Nakib", 15,3.22);
    cse.addStudent(s4);
    System.out.println("6=============");
    s4.setId(25);
    cse.addStudent(s4);
    System.out.println("7=============");
    cse.details();
    System.out.println("8=============");
    Student s5 = new Student("Sakib", 30,2.29);
    cse.addStudent(s5);
    System.out.println("9=============");
    cse.details();
  }
}
"""}

task4 = {
"Cargo.java": """package task4;

public class Cargo {
  private String name;
  private int weight;

  public Cargo(String n, int w) {
    name = n;
    weight = w;
  }

  public String getName() { return name; }
  public int getWeight() { return weight; }
}
""",
"Spaceship.java": """package task4;

public class Spaceship {
  private String name;
  private int capacity;
  private Cargo[] cargos = new Cargo[100];
  private int count = 0;
  private int current = 0;

  public Spaceship(String n, int c) {
    name = n;
    capacity = c;
  }

  public void loadCargo(Cargo c) {
    if (c == null) return;
    int after = current + c.getWeight();
    if (after > capacity) {
      System.out.println("Warning: Unable to load " + c.getName() + " inside " + name + ". Exceeds capacity by " + (after - capacity) + ".");
      return;
    }
    if (count >= cargos.length) return;
    cargos[count] = c;
    count++;
    current = after;
  }

  public void displayDetails() {
    System.out.println("Spaceship Name: " + name);
    System.out.println("Capacity: " + capacity);
    System.out.println("Current Cargo Weight: " + current);
    String s = "";
    int i;
    for (i = 0; i < count; i++) {
      if (i > 0) s = s + " ";
      s = s + cargos[i].getName();
    }
    System.out.println("Cargo:" + s);
  }
}
""",
"Tester2.java": """package task4;

public class Tester2 {
  public static void main(String[] args) {
    Spaceship falcon = new Spaceship("Falcon", 50000);
    Spaceship apollo = new Spaceship("Apollo", 100000);
    Spaceship enterprise = new Spaceship("Enterprise",220000);
    System.out.println("1.===========");
    Cargo gold = new Cargo("Gold", 20000);
    Cargo platinum = new Cargo("Platinum", 25000);
    Cargo dilithium = new Cargo("Dilithium", 50000);
    Cargo trilithium = new Cargo("Trilithium", 70000);
    Cargo neutronium = new Cargo("Neutronium", 80000);
    System.out.println("2.===========");
    falcon.loadCargo(gold);
    falcon.loadCargo(platinum);
    falcon.displayDetails();
    System.out.println("3.===========");
    apollo.loadCargo(gold);
    apollo.displayDetails();
    System.out.println("4.===========");
    falcon.loadCargo(neutronium);
    System.out.println("5.===========");
    enterprise.loadCargo(dilithium);
    enterprise.loadCargo(trilithium);
    enterprise.loadCargo(neutronium);
    enterprise.displayDetails();
  }
}
"""}

for fn, content in task3.items():
    with open(os.path.join("task3", fn), "w", encoding="utf-8") as f:
        f.write(content)

for fn, content in task4.items():
    with open(os.path.join("task4", fn), "w", encoding="utf-8") as f:
        f.write(content)

print("Files written to task3 and task4.")
