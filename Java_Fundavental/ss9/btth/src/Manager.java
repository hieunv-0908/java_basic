public class Manager extends Employee {

    private double allowance;

    public Manager(String name, double baseSalary, double allowance) {
        super(name, baseSalary); // gọi constructor lớp cha
        this.allowance = allowance;
    }

    @Override
    double getSalary() {
        return super.getSalary() + allowance;
    }
}
