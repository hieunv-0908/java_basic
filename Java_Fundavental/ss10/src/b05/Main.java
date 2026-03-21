package b05;

public class Main {
    public static void main(String[] args) {

        Employee staff = new OfficeStaff("An", 8000);
        Employee manager = new Manager("Binh", 15000, 5000);

        System.out.println("Office Staff Salary: " + staff.calculateSalary());
        System.out.println("Manager Salary: " + manager.calculateSalary());
    }

    static interface BonusCalculator {
        double getBonus();
    }

    abstract static class Employee {
        protected String name;
        protected double baseSalary;

        public Employee(String name, double baseSalary) {
            this.name = name;
            this.baseSalary = baseSalary;
        }

        public abstract double calculateSalary();
    }

    static class Manager extends Employee implements BonusCalculator {

        private double bonus;

        public Manager(String name, double baseSalary, double bonus) {
            super(name, baseSalary);
            this.bonus = bonus;
        }

        @Override
        public double getBonus() {
            return bonus;
        }

        @Override
        public double calculateSalary() {
            return baseSalary + getBonus();
        }
    }

    static class OfficeStaff extends Employee {

        public OfficeStaff(String name, double baseSalary) {
            super(name, baseSalary);
        }

        @Override
        public double calculateSalary() {
            return baseSalary;
        }
    }
}