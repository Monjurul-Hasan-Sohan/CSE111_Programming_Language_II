public class StudentTester{
    public static void main (String args[]){
      CSEStudent.details();
      System.out.println("1--------------");
      CSEStudent s1 =  new CSEStudent("Bob", 23);
      s1.info();
      System.out.println("2--------------");
      CSEStudent s2 =  new CSEStudent("Don", 33);
      s2.info();
      System.out.println("3--------------");
      s1.addLabBasedCourse("CSE220");
      s1.addLabBasedCourse("CSE221");
      System.out.println("4--------------");
      s1.info();
      System.out.println("5--------------");
      CSEStudent.details();
      System.out.println("6--------------");
      s1.addLabBasedCourse("CSE230");
      System.out.println("7--------------");
      s1.info();
      System.out.println("8--------------");
      s2.addLabBasedCourse("CSE110");
      s2.info();
    }
  }
  
  class Student{
    public String name;
    public int id;
    public String courses = "";
    
    public Student(String n, int i){
      name = n;
      id = i;
    }
    
    public void info(){
      System.out.println("Name: "+name);
      System.out.println("ID: "+id);
      System.out.println("Courses: "+courses);
    }
  }

  
  class CSEStudent extends Student {
    private static final String[] LAB_BASED = {"CSE110", "CSE111", "CSE220", "CSE221"};
    public static int count = 0;

    public CSEStudent(String n, int i) {
        super(n, i);
        count++;
    }

    public static void details() {
        System.out.println("Total CSE Students: " + count);
        System.out.println("Available Lab Based Courses:");
        System.out.println("CSE110 CSE111 CSE220 CSE221");
    }

    public void addLabBasedCourse(String code) {
        boolean ok = false;
        for (String c : LAB_BASED) {
            if (c.equals(code)) {
              ok = true;
              break;
            }
        }
        if (!ok) {
            System.out.println("It is not a lab based course!");
            return;
        }
        if (courses == null || courses.isEmpty()) courses = code;
        else courses += " " + code;
    }
}
