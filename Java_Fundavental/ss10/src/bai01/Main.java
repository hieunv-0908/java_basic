package bai01;

public class Main {
    public static void main(String[] args) {
        Shape circle = new Circle(5);
        Shape rectangle = new Rectangle(4, 6);

        System.out.println("Hinh tron:");
        System.out.println("Dien tich: " + circle.getArea());
        System.out.println("Chu vi: " + circle.getPerimeter());

        System.out.println();

        System.out.println("Hinh chu nhat:");
        System.out.println("Dien tich: " + rectangle.getArea());
        System.out.println("Chu vi: " + rectangle.getPerimeter());
    }
}