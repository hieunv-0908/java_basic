abstract public class Drink {
    private int id;
    String name;
    float price;
    String type;

    public Drink(int id, String name, float price, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    abstract public double calculatePrice();

    void displayInfo() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price);
    }

    public void mix() {
    }
}
