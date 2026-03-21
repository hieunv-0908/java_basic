package btth;

import java.util.Scanner;

public class Manager extends Employee {
    private String position;
    private String department;

    public Manager() {}

    public Manager(int id, String name, int age, double credit, double baseSalary, String position, String department) {
        super(id, name, age, credit, baseSalary);
        this.position = position;
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void inputManager(Scanner sc) {
        super.inputData(sc);
        sc.nextLine();
        System.out.print("Nhap chuc vu: ");
        position = sc.nextLine();
        System.out.print("Nhap phong ban: ");
        department = sc.nextLine();
    }

    @Override
    public void displayData() {
        System.out.println("ID: " + id + " | Ten: " + name + " | Tuoi: " + age + " | He so: " + credit + " | Luong co ban: " + baseSalary + " | Luong: " + salary + " | Chuc vu: " + position + " | Phong ban: " + department);
    }

    @Override
    public void calcSalary() {
        salary = credit * baseSalary * 1.2;
    }
}

