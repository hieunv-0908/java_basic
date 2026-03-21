package bai01;

public class BookingCounterDeadlock extends Thread {
    private String counterName;
    private TicketPool firstRoom;
    private TicketPool secondRoom;

    public BookingCounterDeadlock(String counterName, TicketPool firstRoom, TicketPool secondRoom) {
        this.counterName = counterName;
        this.firstRoom = firstRoom;
        this.secondRoom = secondRoom;
    }

    @Override
    public void run() {
        sellCombo();
    }

    public void sellCombo() {
        String ticket1 = null;
        String ticket2 = null;

        synchronized (firstRoom) {
            ticket1 = firstRoom.getTicket();

            if (ticket1 != null) {
                System.out.println(counterName + ": Đã lấy vé " + ticket1);
            } else {
                System.out.println(counterName + ": Hết vé phòng " + firstRoom.getRoomName() + ", bán combo thất bại");
                return;
            }

            try {
                Thread.sleep(100); // tạo điều kiện dễ xảy ra deadlock
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(counterName + ": Đang chờ vé " + secondRoom.getRoomName() + "...");

            synchronized (secondRoom) {
                ticket2 = secondRoom.getTicket();

                if (ticket2 != null) {
                    System.out.println(counterName + " bán combo thành công: " + ticket1 + " & " + ticket2);
                } else {
                    System.out.println(counterName + ": Hết vé phòng " + secondRoom.getRoomName() + ", trả lại " + ticket1);
                    firstRoom.returnTicket(ticket1);
                }
            }
        }
    }
}