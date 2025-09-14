package CLASSWORK4.Task 3;

class Teacher {
    private String name;
    private String initial;
    private Course[] courseList;
    private int courseCount;

    public Teacher(String name, String initial) {
        this.name = name;
        this.initial = initial;
        courseList = new Course[3];  // max 3 courses
        courseCount = 0;
        System.out.println("A new teacher has been created");
    }

    public void addCourse(Course c) {
        if (courseCount < 3) {
            courseList[courseCount] = c;
            courseCount++;
        }
    }

    public void printDetail() {
        System.out.println("Name: " + name);
        System.out.println("Initial: " + initial);
        System.out.println("List of courses:");
        for (int i = 0; i < courseCount; i++) {
            System.out.println(courseList[i]);
        }
    }
}
