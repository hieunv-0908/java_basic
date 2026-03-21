package bai04;

public class EmergencyPatient {

    private String id;
    private String name;
    private int priority; // 1: cấp cứu, 2: thường
    private long order;   // thứ tự vào hàng

    public EmergencyPatient(String id, String name, int priority, long order) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public long getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return id + " - " + name + " - Priority: " + priority;
    }
}