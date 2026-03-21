public class Coffee extends Drink{
    boolean hasMilk;

    public Coffee(int id, String name, float price, boolean hasMilk,String type) {
        super(id, name, price, type);
        this.hasMilk = hasMilk;
    }

    @Override
    public double calculatePrice(){
        if(hasMilk){
            this.price += 5000;
        };
        return this.price;
    };

    @Override
    void displayInfo() {
        super.displayInfo();
        if(hasMilk){
            System.out.println("Có sữa.");
        } else {
            System.out.println("Đen đá.");
        }
    }
}
