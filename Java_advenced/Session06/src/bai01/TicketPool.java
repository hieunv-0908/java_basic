package bai01;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private String roomName;
    private Queue<String> tickets = new LinkedList<>();

    public TicketPool(String roomName, int totalTickets) {
        this.roomName = roomName;
        for (int i = 1; i <= totalTickets; i++) {
            tickets.add(roomName + "-" + String.format("%03d", i));
        }
    }

    public String getRoomName() {
        return roomName;
    }

    public String getTicket() {
        return tickets.poll(); // lấy 1 vé, nếu hết thì trả về null
    }

    public void returnTicket(String ticket) {
        if (ticket != null) {
            tickets.add(ticket);
        }
    }

    public int remainingTickets() {
        return tickets.size();
    }
}