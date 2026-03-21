public class Main {
    public static void main(String[] args) {

        Employee e1 = new Manager("An", 1000, 500);
        Employee e2 = new Developer("Bình", 1000, 20, 15);

        System.out.println("Lương Manager: " + e1.getSalary());
        System.out.println("Lương Developer: " + e2.getSalary());

        System.out.println("Lương có thưởng: " + e1.getSalary(200));
    }
}
