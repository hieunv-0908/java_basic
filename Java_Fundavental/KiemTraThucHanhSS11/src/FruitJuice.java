public class FruitJuice extends Drink implements IMixable {
    int discountPercent;

    public FruitJuice(int id, String name, float price, int discountPercent, String type) {
        super(id, name, price, type);
        this.discountPercent = discountPercent;
    }

    @Override
    public double calculatePrice(){
        return this.price - (this.price * discountPercent / 100);
    }

    @Override
    public void mix(){
        System.out.println("Đang ép trái cây tươi...");
    }
}
