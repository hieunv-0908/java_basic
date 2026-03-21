package bai02;

abstract public class Vahicle {
    protected String brand;

    public Vahicle(String brand) {
        this.brand = brand;
    }

    abstract public void move();
}
