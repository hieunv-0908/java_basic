package bai02;

public class Car extends Vahicle {
    public Car(String brand) {
        super(brand);
    }

    @Override
    public void move() {
        System.out.println("Di chuyển bằng động cơ.");
    }
}
