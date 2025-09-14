package task6;
public class InPatient extends Patient {
    private final String department;

    public InPatient(String name, String doctor, String department) {
        super(name, doctor, true);
        this.department = department;
        System.out.println("New patient admitted in " + department + ".");
    }

    public static void details() {
        System.out.println("Total patients: " + Patient.getTotalPatients() + ".");
        System.out.println("Admitted In-Patients: " + Patient.getInPatients() + ".");
        System.out.println("Out-Patients: " + Patient.getOutPatients() + ".");
    }

    @Override
    public String toString() {
        return super.toString() + "\nDepartment: " + department;
    }
}
