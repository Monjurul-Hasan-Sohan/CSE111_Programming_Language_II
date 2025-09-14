package task3;

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
