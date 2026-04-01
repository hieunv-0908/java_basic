package model;

import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int userId;
    private int machineId;
    private Timestamp startTime;
    private Timestamp endTime;
    private BookingStatus status;

    public Booking(int bookingId, int userId, int machineId, Timestamp startTime, Timestamp endTime, BookingStatus status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.machineId = machineId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Booking() {
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
