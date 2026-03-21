import java.util.PriorityQueue;

public class EmergencyQueue {

    private PriorityQueue<EmergencyPatient> queue;
    private long counter = 0;

    public EmergencyQueue() {
        queue = new PriorityQueue<>((p1, p2) -> {
            if (p1.getPriority() != p2.getPriority()) {
                return p1.getPriority() - p2.getPriority();
                // priority nhỏ hơn -> ưu tiên cao hơn
            }
            return Long.compare(p1.getOrder(), p2.getOrder());
            // nếu cùng priority -> theo thứ tự vào (FIFO)
        });
    }

    public void addPatient(EmergencyPatient p) {
        queue.add(p);
    }

    public EmergencyPatient callNextPatient() {
        return queue.poll();
    }

    public void displayQueue() {
        for (EmergencyPatient p : queue) {
            System.out.println(p);
        }
    }

    public long getNextOrder() {
        return counter++;
    }
}