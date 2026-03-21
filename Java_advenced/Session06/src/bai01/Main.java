package bai01;

public class Main {
    public static void main(String[] args) {
//        TicketPool roomA = new TicketPool("A", 2);
//        TicketPool roomB = new TicketPool("B", 2);
//
//        BookingCounterDeadlock counter1 = new BookingCounterDeadlock("Quầy 1", roomA, roomB);
//        BookingCounterDeadlock counter2 = new BookingCounterDeadlock("Quầy 2", roomB, roomA);
//
//        counter1.start();
//        counter2.start();
    
        TicketPool roomA = new TicketPool("A", 2);
        TicketPool roomB = new TicketPool("B", 2);

        BookingCounterSafe counter1 = new BookingCounterSafe("Quầy 1", roomA, roomB);
        BookingCounterSafe counter2 = new BookingCounterSafe("Quầy 2", roomA, roomB);
        BookingCounterSafe counter3 = new BookingCounterSafe("Quầy 3", roomA, roomB);

        counter1.start();
        counter2.start();
        counter3.start();
    }
}
