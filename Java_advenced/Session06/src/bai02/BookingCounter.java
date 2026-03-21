package bai02;

public class BookingCounter extends Thread {
    private String counterName;
    private TicketPool ticketPool;
    private int numberOfTicketsToSell;

    public BookingCounter(String counterName, TicketPool ticketPool, int numberOfTicketsToSell) {
        this.counterName = counterName;
        this.ticketPool = ticketPool;
        this.numberOfTicketsToSell = numberOfTicketsToSell;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfTicketsToSell; i++) {
            String ticket = ticketPool.sellTicket(counterName);

            if (ticket != null) {
                break;
            }

            try {
                Thread.sleep(500); // giả lập thời gian xử lý bán vé
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(counterName + ": Bị gián đoạn khi bán vé");
                break;
            }
        }
    }
}