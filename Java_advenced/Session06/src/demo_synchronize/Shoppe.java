package demo_synchronize;

import javax.swing.plaf.TableHeaderUI;

public class Shoppe extends Thread{
    private String customerName;

    public Shoppe(String customerName) {
        this.customerName = customerName;
    }

    public Shoppe() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    @Override
    public void run() {
        System.out.println(customerName + "Trước khi bán");
    }
}
