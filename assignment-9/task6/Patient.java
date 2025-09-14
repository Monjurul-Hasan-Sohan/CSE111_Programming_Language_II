package task6;
public class Patient {
    private static int seq = 1;
    private static int totalPatients = 0;
    private static int inPatients = 0;
    private static int outPatients = 0;

    private final String id;
    private final String name;
    private final String doctor;

    protected Patient(String name, String doctor, boolean isInPatient) {
        this.id = String.format("P%02d", seq++);
        this.name = name;
        this.doctor = doctor;
        totalPatients++;
        if (isInPatient) inPatients++; else outPatients++;
    }

    public Patient(String name, String doctor) {
        this(name, doctor, false);
    }

    public static void details() {
        System.out.println("Total patients: " + totalPatients + ".");
    }

    public static void details(Patient[] people) {
        System.out.println("Details of " + people.length + " selected patients:");
        for (Patient p : people) {
            System.out.println("== == == ==");
            System.out.println(p.toString());
        }
    }

    public static int getTotalPatients() {
        return totalPatients;
    }

    public static int getInPatients() { return inPatients; }
    public static int getOutPatients() { return outPatients; }

    @Override
    public String toString() {
        return "Patient ID: " + id + ", Name: " + name + "\nDoctor: " + doctor;
    }
}
