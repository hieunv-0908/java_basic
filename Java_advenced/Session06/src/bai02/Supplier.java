package bai02;

public class Supplier extends Thread {
    private TicketPool ticketPool;
    private int ticketsToAdd;
    private long delay;

    public Supplier(TicketPool ticketPool, int ticketsToAdd, long delay) {
        this.ticketPool = ticketPool;
        this.ticketsToAdd = ticketsToAdd;
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delay); // giả lập thời gian sau mới thêm vé
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Nhà cung cấp bị gián đoạn");
            return;
        }

        ticketPool.addTickets(ticketsToAdd);
    }
}