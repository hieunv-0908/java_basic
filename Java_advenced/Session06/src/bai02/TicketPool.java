package bai02;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private String roomName;
    private Queue<String> tickets = new LinkedList<>();
    private int nextTicketNumber = 1;

    public TicketPool(String roomName, int initialTickets) {
        this.roomName = roomName;
        addInitialTickets(initialTickets);
    }

    private void addInitialTickets(int count) {
        for (int i = 0; i < count; i++) {
            tickets.add(generateTicketCode());
        }
    }

    private String generateTicketCode() {
        return roomName + "-" + String.format("%03d", nextTicketNumber++);
    }

    public String getRoomName() {
        return roomName;
    }

    public synchronized String sellTicket(String counterName) {
        while (tickets.isEmpty()) {
            System.out.println(counterName + ": Hết vé phòng " + roomName + ", đang chờ...");
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(counterName + ": Bị gián đoạn khi chờ vé phòng " + roomName);
                return null;
            }
        }

        String ticket = tickets.poll();
        System.out.println(counterName + " bán vé " + ticket);
        return ticket;
    }

    public synchronized void addTickets(int count) {
        for (int i = 0; i < count; i++) {
            tickets.add(generateTicketCode());
        }

        System.out.println("Nhà cung cấp: Đã thêm " + count + " vé vào phòng " + roomName);
        notifyAll();
    }

    public synchronized int getRemainingTickets() {
        return tickets.size();
    }
}