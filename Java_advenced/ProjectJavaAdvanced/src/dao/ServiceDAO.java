package dao;

import model.*;
import ultil.SqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {
    static Connection connection = SqlConnection.getconnection();

    public static List<Service> queryDb() {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM services";
        try {
            PreparedStatement prsmt = connection.prepareStatement(sql);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                int serviceId = resultSet.getInt("service_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");

                Service service;
                service = new Service(
                        serviceId,
                        name,
                        description,
                        price,
                        stock
                );
                list.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean createService() {

        return true;
    }
}
