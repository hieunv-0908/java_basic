package bai02;

public class Bicycle extends Vahicle {
    public Bicycle(String brand) {
        super(brand);
    }

    @Override
    public void move() {
        System.out.println("Di chuyển bằng sức người.");
    }
}
