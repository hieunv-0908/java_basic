package bai04;

public class Main {
    public static void main(String[] args) {

        EmergencyQueue eq = new EmergencyQueue();

        eq.addPatient(new EmergencyPatient("P1", "An", 2, eq.getNextOrder()));
        eq.addPatient(new EmergencyPatient("P2", "Bình", 1, eq.getNextOrder()));
        eq.addPatient(new EmergencyPatient("P3", "Cường", 1, eq.getNextOrder()));
        eq.addPatient(new EmergencyPatient("P4", "Dũng", 2, eq.getNextOrder()));

        System.out.println("Gọi bệnh nhân:");
        System.out.println(eq.callNextPatient());
        System.out.println(eq.callNextPatient());
    }
}
