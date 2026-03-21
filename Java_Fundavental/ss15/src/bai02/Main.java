package bai02;

public class Main {
    public static void main(String[] args) {

        PatientQueue patientQueue = new PatientQueue();

        patientQueue.addPatient(new Patient("P01", "Nguyễn Văn A", 25));
        patientQueue.addPatient(new Patient("P02", "Trần Thị B", 30));
        patientQueue.addPatient(new Patient("P03", "Lê Văn C", 40));

        patientQueue.displayQueue();

        System.out.println("\nBệnh nhân tiếp theo:");
        System.out.println(patientQueue.peekNextPatient());

        System.out.println("\nGọi khám:");
        System.out.println(patientQueue.callNextPatient());

        System.out.println("\nDanh sách còn lại:");
        patientQueue.displayQueue();
    }
}