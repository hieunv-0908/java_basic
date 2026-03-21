import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Employee> employees = new ArrayList<>();

        employees.add(new OfficeEmployee("Nguyen Van A", 10_000_000));
        employees.add(new ProductionEmployee("Tran Thi B", 300, 20_000));
        employees.add(new ProductionEmployee("Le Van C", 150, 30_000));

        double totalSalary = 0;

        System.out.println("Danh sách lương nhân viên:");

        int index = 1;
        for (Employee e : employees) {
            double salary = e.calculateSalary(); // ĐA HÌNH
            totalSalary += salary;

            System.out.print(index + ". " + e.getName()
                    + " (" + e.getType() + ") - Lương: "
                    + String.format("%,.0f", salary));

            if (e instanceof ProductionEmployee) {
                ProductionEmployee p = (ProductionEmployee) e;
                System.out.print(" (" + p.getDetail() + ")");
            }

            System.out.println();
            index++;
        }

        System.out.println("\n=> TỔNG LƯƠNG CÔNG TY: "
                + String.format("%,.0f", totalSalary));
    }
}
