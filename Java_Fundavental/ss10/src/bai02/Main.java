package bai02;

public class Main {
    public static void main(String args[]){
        Vahicle car = new Car("Toyota");
        Vahicle bicycle = new Bicycle("Giant");

        System.out.println("Thương hiệu: " + car.brand);
        System.out.print("Phương thức di chuyển: "); car.move();

        System.out.println();

        System.out.println("Thương hiệu: " + bicycle.brand);
        System.out.print("Phương thức di chuyển: "); bicycle.move();
    }
}
