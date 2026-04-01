package dao;

import model.Booking;
import model.BookingStatus;
import ultil.SqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    static Connection connection = SqlConnection.getconnection();

    public static List<Booking> queryDb() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int bookingId = resultSet.getInt("booking_id");
                int userId = resultSet.getInt("user_id");
                int machineId = resultSet.getInt("machine_id");
                Timestamp startTime = resultSet.getTimestamp("start_time");
                Timestamp endTime = resultSet.getTimestamp("end_time");
                BookingStatus status = BookingStatus.fromStatus(resultSet.getString("status"));

                Booking booking = new Booking(bookingId, userId, machineId, startTime, endTime, status);
                list.add(booking);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn database: " + e.getMessage());
        }

        return list;
    }

    public static boolean createBooking(int userId, int machineId, Timestamp startTime, Timestamp endTime) {
        String sql = "INSERT INTO bookings (user_id, machine_id, start_time, end_time, status) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prsmt.setInt(1, userId);
            prsmt.setInt(2, machineId);
            prsmt.setTimestamp(3, startTime);
            prsmt.setTimestamp(4, endTime);
            prsmt.setString(5, BookingStatus.BOOKED.getStatusCode());
            int rowsAffected = prsmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = prsmt.getGeneratedKeys();
                if (rs.next()) {
                    int bookingId = rs.getInt(1);
                    System.out.println("Booking created with ID: " + bookingId);
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tạo booking: " + e.getMessage());
        }
        return false;
    }

    public static boolean isMachineAvailable(int machineId, Timestamp startTime, Timestamp endTime) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE machine_id = ? AND status IN ('BOOKED', 'USING') AND ((start_time < ? AND end_time > ?) OR (start_time < ? AND end_time > ?) OR (start_time >= ? AND end_time <= ?))";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, machineId);
            prsmt.setTimestamp(2, endTime);
            prsmt.setTimestamp(3, startTime);
            prsmt.setTimestamp(4, startTime);
            prsmt.setTimestamp(5, endTime);
            prsmt.setTimestamp(6, startTime);
            prsmt.setTimestamp(7, endTime);
            ResultSet rs = prsmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kiểm tra máy: " + e.getMessage());
        }
        return false;
    }

    public static int getLastInsertedId() {
        String sql = "SELECT LAST_INSERT_ID()";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            ResultSet rs = prsmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy ID: " + e.getMessage());
        }
        return -1;
    }

    public static List<Booking> getBookingsByMachineId(int machineId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE machine_id = ? AND status IN ('BOOKED', 'USING') ORDER BY start_time";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, machineId);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int bookingId = resultSet.getInt("booking_id");
                int userId = resultSet.getInt("user_id");
                Timestamp startTime = resultSet.getTimestamp("start_time");
                Timestamp endTime = resultSet.getTimestamp("end_time");
                BookingStatus status = BookingStatus.fromStatus(resultSet.getString("status"));

                Booking booking = new Booking(bookingId, userId, machineId, startTime, endTime, status);
                list.add(booking);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn booking của máy: " + e.getMessage());
        }

        return list;
    }

    public static List<Booking> getPendingBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE status IN ('BOOKED', 'USING') ORDER BY start_time";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int bookingId = resultSet.getInt("booking_id");
                int userId = resultSet.getInt("user_id");
                int machineId = resultSet.getInt("machine_id");
                Timestamp startTime = resultSet.getTimestamp("start_time");
                Timestamp endTime = resultSet.getTimestamp("end_time");
                BookingStatus status = BookingStatus.fromStatus(resultSet.getString("status"));

                Booking booking = new Booking(bookingId, userId, machineId, startTime, endTime, status);
                list.add(booking);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn booking đang chờ: " + e.getMessage());
        }

        return list;
    }

    public static boolean updateBookingStatus(int bookingId, String newStatus) {
        String sql = "UPDATE bookings SET status = ? WHERE booking_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setString(1, newStatus);
            prsmt.setInt(2, bookingId);
            int rowsAffected = prsmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cập nhật trạng thái booking thành công!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật booking: " + e.getMessage());
        }
        return false;
    }

    public static Booking getBookingById(int bookingId) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            prsmt.setInt(1, bookingId);
            ResultSet resultSet = prsmt.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int machineId = resultSet.getInt("machine_id");
                Timestamp startTime = resultSet.getTimestamp("start_time");
                Timestamp endTime = resultSet.getTimestamp("end_time");
                BookingStatus status = BookingStatus.fromStatus(resultSet.getString("status"));

                return new Booking(bookingId, userId, machineId, startTime, endTime, status);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn booking: " + e.getMessage());
        }
        return null;
    }
}
