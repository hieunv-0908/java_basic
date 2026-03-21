package bai01;

public class BookingCounterSafe extends Thread {
    private String counterName;
    private TicketPool roomA;
    private TicketPool roomB;

    public BookingCounterSafe(String counterName, TicketPool roomA, TicketPool roomB) {
        this.counterName = counterName;
        this.roomA = roomA;
        this.roomB = roomB;
    }

    @Override
    public void run() {
        sellCombo();
    }

    public void sellCombo() {
        TicketPool firstLock;
        TicketPool secondLock;

        // Khóa theo thứ tự nhất quán
        if (roomA.getRoomName().compareTo(roomB.getRoomName()) < 0) {
            firstLock = roomA;
            secondLock = roomB;
        } else {
            firstLock = roomB;
            secondLock = roomA;
        }

        String ticketA = null;
        String ticketB = null;

        synchronized (firstLock) {
            synchronized (secondLock) {
                // lấy vé đúng theo roomA, roomB chứ không phải theo thứ tự lock
                ticketA = roomA.getTicket();
                ticketB = roomB.getTicket();

                if (ticketA != null && ticketB != null) {
                    System.out.println(counterName + " bán combo thành công: " + ticketA + " & " + ticketB);
                } else {
                    // hoàn vé nếu đã lỡ lấy 1 bên
                    if (ticketA != null) {
                        roomA.returnTicket(ticketA);
                    }
                    if (ticketB != null) {
                        roomB.returnTicket(ticketB);
                    }

                    if (ticketA == null) {
                        System.out.println(counterName + ": Hết vé phòng " + roomA.getRoomName() + ", bán combo thất bại");
                    } else {
                        System.out.println(counterName + ": Hết vé phòng " + roomB.getRoomName() + ", bán combo thất bại");
                    }
                }
            }
        }
    }
}
