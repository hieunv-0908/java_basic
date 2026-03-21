import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Shape> shapes = new ArrayList<>();

        // Sử dụng Overloading constructor
        shapes.add(new Circle(5));
        shapes.add(new Rectangle(3, 4));
        shapes.add(new Rectangle(6)); // hình vuông

        double totalArea = 0;
        int index = 1;

        System.out.println("Kết quả tính toán hình học:\n");

        for (Shape s : shapes) {
            double area = s.calculateArea(); // Overriding (Runtime)
            totalArea += area;

            if (s instanceof Circle) {
                Circle c = (Circle) s;
                System.out.printf(
                        "%d. Hình tròn (r=%.0f) - Diện tích: %.2f%n%n",
                        index, c.getRadius(), area
                );
            } else if (s instanceof Rectangle) {
                Rectangle r = (Rectangle) s;
                if (r.isSquare()) {
                    System.out.printf(
                            "%d. Hình vuông (cạnh %.1f) - Diện tích: %.2f%n%n",
                            index, r.getWidth(), area
                    );
                } else {
                    System.out.printf(
                            "%d. Hình chữ nhật (%.1f x %.1f) - Diện tích: %.2f%n%n",
                            index, r.getWidth(), r.getHeight(), area
                    );
                }
            }
            index++;
        }

        System.out.printf("=> Tổng diện tích các hình: %.2f", totalArea);
    }
}
