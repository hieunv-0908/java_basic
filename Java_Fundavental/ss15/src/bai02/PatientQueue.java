import java.util.LinkedList;
import java.util.Queue;

public class PatientQueue {

    private Queue<Patient> queue;

    // Constructor
    public PatientQueue() {
        queue = new LinkedList<>();
    }

    // Thêm bệnh nhân (enqueue)
    public void addPatient(Patient p) {
        queue.offer(p);
        System.out.println("Đã thêm bệnh nhân vào hàng chờ.");
    }

    // Gọi bệnh nhân tiếp theo (dequeue)
    public Patient callNextPatient() {
        if (queue.isEmpty()) {
            System.out.println("Không có bệnh nhân nào trong hàng chờ.");
            return null;
        }
        Patient p = queue.poll();
        System.out.println("Đang gọi bệnh nhân...");
        return p;
    }

    // Xem bệnh nhân tiếp theo (peek)
    public Patient peekNextPatient() {
        if (queue.isEmpty()) {
            System.out.println("Hàng chờ đang rỗng.");
            return null;
        }
        return queue.peek();
    }

    // Kiểm tra rỗng
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Hiển thị toàn bộ hàng chờ
    public void displayQueue() {
        if (queue.isEmpty()) {
            System.out.println("Không có bệnh nhân nào đang chờ.");
            return;
        }

        System.out.println("===== DANH SÁCH BỆNH NHÂN ĐANG CHỜ =====");
        for (Patient p : queue) {
            System.out.println(p);
        }
    }
}