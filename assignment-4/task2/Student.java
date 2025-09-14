package task2;

class Student {
    int studentId;
    double gradePoint;
    boolean cgSet;
    String[] courseList = new String[4];
    int courseIndex;

    Student(int id) {
        studentId = id;
        System.out.println("A student with ID " + id + " has been created.");
    }

    Student(int id, double gp) {
        studentId = id;
        gradePoint = gp;
        cgSet = true;
        System.out.println("A student with ID " + id + " and cgpa " + gp + " has been created.");
    }

    void storeCG(double gp) {
        gradePoint = gp;
        cgSet = true;
    }

    void storeID(int id) {
        studentId = id;
    }

    void addCourse(String code) {
        if (!cgSet) {
            System.out.println("Failed to add " + code);
            System.out.println("Set CG first");
            return;
        }
        int limit;
        if (gradePoint < 3) {
            limit = 3;
        } else {
            limit = 4;
        }
        if (courseIndex >= limit) {
            System.out.println("Failed to add " + code);
            if (gradePoint < 3) {
                System.out.println("CG is low. Can't add more than 3 courses.");
            } else {
                System.out.println("Maximum 4 courses allowed.");
            }
            return;
        }
        courseList[courseIndex++] = code;
    }

    void addCourse(String[] codes) {
        for (int i = 0; i < codes.length; i++) {
            addCourse(codes[i]);
        }
    }

    void removeAllCourse() {
        courseList = new String[4];
        courseIndex = 0;
    }

    void showAdvisee() {
        System.out.println("Student ID: " + studentId + ", CGPA: " + gradePoint);
        if (courseIndex == 0) {
            System.out.println("No courses added.");
        } else {
            System.out.println("Added courses are:");
            for (int i = 0; i < courseIndex; i++) {
                System.out.print(courseList[i]);
                if (i < courseIndex - 1) System.out.print(" ");
            }
            System.out.println();
        }
    }
}
