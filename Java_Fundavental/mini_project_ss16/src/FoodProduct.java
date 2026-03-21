public class FoodProduct extends Product{
    int discountPercent;

    public FoodProduct(String id, String name, double price, int discountPercent) {
        super(id, name, price);
        this.discountPercent = discountPercent;
    }

    @Override
    double calculateFinalPrice() {
        return price - (price * discountPercent / 100);
    }

    @Override
    void displayInfo() {
        super.displayInfo();
        System.out.println("Phần trăm giảm giá: " + discountPercent + "%");
    }
}
