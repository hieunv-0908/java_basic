package demo_synchronize;

public class Warehouese {
    private int quantity;

    public Warehouese(int quantity) {
        this.quantity = quantity;
    }

    public Warehouese() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void sellProduct(){
        quantity --;
    }
}
