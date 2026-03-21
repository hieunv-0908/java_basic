package btth;

import java.util.Scanner;

public class Employee {
    protected int id;
    protected String name;
    protected int age;
    protected double credit;
    protected double baseSalary;
    protected double salary;

    public Employee() {}

    public Employee(int id, String name, int age, double credit, double baseSalary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.credit = credit;
        this.baseSalary = baseSalary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getSalary() {
        return salary;
    }

    public void inputData(Scanner sc) {
        System.out.print("Nhap ma: ");
        id = sc.nextInt();
        sc.nextLine();
        System.out.print("Nhap ten: ");
        name = sc.nextLine();
        System.out.print("Nhap tuoi: ");
        age = sc.nextInt();
        System.out.print("Nhap he so luong: ");
        credit = sc.nextDouble();
        System.out.print("Nhap luong co ban: ");
        baseSalary = sc.nextDouble();
    }

    public void displayData() {
        System.out.println("ID: " + id + " | Ten: " + name + " | Tuoi: " + age + " | He so: " + credit + " | Luong co ban: " + baseSalary + " | Luong: " + salary);
    }

    public void calcSalary() {
        salary = credit * baseSalary;
    }
}

