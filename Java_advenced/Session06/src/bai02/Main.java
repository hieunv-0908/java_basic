package bai02;

public class Main {
    public static void main(String[] args) {
        TicketPool roomA = new TicketPool("A", 2);
        TicketPool roomB = new TicketPool("B", 5);

        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA, 5);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomB, 5);

        Supplier supplierA = new Supplier(roomA, 3, 3000);

        counter1.start();
        counter2.start();
        supplierA.start();

        try {
            counter1.join();
            counter2.join();
            supplierA.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main bị gián đoạn");
        }

        System.out.println("Chương trình kết thúc.");
    }
}
