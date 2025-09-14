package task2;
public class Student {
    String name = "Not Set";
    String department = "CSE";
    double cgpa = 0.0;
    int credits = 9;
    String scholarshipStatus = "No scholarship";

    void updateDetails(String name, double cgpa, int credits) {
        this.name = name;
        this.cgpa = cgpa;
        this.credits = credits;
    }

    void updateDetails(String name, double cgpa, int credits, String department) {
        this.name = name;
        this.cgpa = cgpa;
        this.credits = credits;
        this.department = department;
    }

    void updateDetails(String name, double cgpa) {
        this.name = name;
        this.cgpa = cgpa;
    }

    void checkScholarshipEligibility() {
        if (cgpa >= 3.7) {
            scholarshipStatus = "Merit based scholarship";
            System.out.println(name + " is eligible for Merit based scholarship");
        } else if (cgpa >= 3.5 && cgpa < 3.7 && credits > 10) {
            scholarshipStatus = "Need based scholarship";
            System.out.println(name + " is eligible for Need based scholarship");
        } else {    
            scholarshipStatus = "No     ";
            System.out.println(name + " is not eligible for scholarship");
        }
    }

    void showDetails() {
        System.out.p    rintln("Name: " + name);
        System.out.println("Department: " + department);
        System.out.println("CGPA: " + cgpa);
        System.out.println("Credits: " + credits);
        System.out.println("Scholarship Status: " + scholarshipStatus);
    }
}
