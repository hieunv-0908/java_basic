import java.util.LinkedList;

class EmergencyRoom {

    private LinkedList<String> queue = new LinkedList<>();

    // Bệnh nhân thường -> vào cuối
    public void patientCheckIn(String name) {
        queue.addLast(name);
        System.out.println(name + " đã check-in (thường).");
    }

    // Ca nguy kịch -> vào đầu
    public void emergencyCheckIn(String name) {
        queue.addFirst(name);
        System.out.println(name + " đã check-in (CẤP CỨU).");
    }

    // Bác sĩ gọi bệnh nhân
    public void treatPatient() {
        if (queue.isEmpty()) {
            System.out.println("Không còn bệnh nhân.");
            return;
        }

        String patient = queue.removeFirst();

        // Nếu là người được thêm bằng emergencyCheckIn
        // (ở đây ta giả lập bằng cách kiểm tra vị trí gọi)
        System.out.println("Đang khám: " + patient);
    }
}