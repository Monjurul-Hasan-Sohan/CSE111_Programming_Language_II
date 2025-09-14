package task3;

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
