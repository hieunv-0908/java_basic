public abstract class Employee {
    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Phương thức trừu tượng – đa hình runtime
    public abstract double calculateSalary();

    public abstract String getType();
}
