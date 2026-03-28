package service;

import dao.ServiceDAO;
import model.Service;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    List<Service> services = ServiceDAO.queryDb();

    public ProductService() {
    }
    
}
