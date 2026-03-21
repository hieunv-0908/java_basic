package demo_method_thread;

public class Worker extends Thread{
    private String fullName;
    private boolean isPolite;

    public Worker(String fullName, boolean isPolite) {
        this.fullName = fullName;
        this.isPolite = isPolite;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public void run() {
        System.out.println("[Công nhân]" + fullName + "Bắt đầu làm việc.");

        for (int i = 0; i < 5; i++) {
            Thread.yield();
            if(isPolite){
                System.out.println("[Công nhân]" + fullName + "Làm việc.");
            }
        }
    }
}
