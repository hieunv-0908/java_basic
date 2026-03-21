import java.util.ArrayList;
import java.util.Iterator;

public class TemperatureManager {
    public static void main(String[] args) {

        // Tạo danh sách ban đầu
        ArrayList<Double> temperatures = new ArrayList<>();
        temperatures.add(36.5);
        temperatures.add(40.2);
        temperatures.add(37.0);
        temperatures.add(12.5);
        temperatures.add(39.8);
        temperatures.add(99.9);
        temperatures.add(36.8);

        System.out.println("Danh sách ban đầu: " + temperatures);

        // Sử dụng Iterator để duyệt và xóa an toàn
        Iterator<Double> iterator = temperatures.iterator();

        while (iterator.hasNext()) {
            Double temp = iterator.next();

            if (temp < 34.0 || temp > 42.0) {
                iterator.remove(); // Xóa an toàn
            }
        }

        System.out.println("Danh sách sau khi lọc: " + temperatures);

        // Tính nhiệt độ trung bình
        double sum = 0;
        for (Double temp : temperatures) {
            sum += temp;
        }

        double average = sum / temperatures.size();
        System.out.printf("Nhiệt độ trung bình: %.2f", average);
    }
}