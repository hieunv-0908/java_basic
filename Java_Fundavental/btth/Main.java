package btth;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Employee[] list = new Employee[100];
        int current = 0;
        int choice;

        do {
            System.out.println("====== MENU ======");
            System.out.println("1. Them nhan vien");
            System.out.println("2. Them quan ly");
            System.out.println("3. Tinh luong tat ca");
            System.out.println("4. Hien thi tat ca");
            System.out.println("5. Sap xep luong giam dan");
            System.out.println("6. Dem nhan vien va quan ly");
            System.out.println("7. Tim kiem theo ten");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: {
                    System.out.print("Nhap so luong nhan vien: ");
                    int n = sc.nextInt();
                    for (int i = 0; i < n; i++) {
                        Employee e = new Employee();
                        e.inputData(sc);
                        list[current++] = e;
                    }
                    break;
                }
                case 2: {
                    System.out.print("Nhap so luong quan ly: ");
                    int n = sc.nextInt();
                    for (int i = 0; i < n; i++) {
                        Manager m = new Manager();
                        m.inputManager(sc);
                        list[current++] = m;
                    }
                    break;
                }
                case 3: {
                    for (int i = 0; i < current; i++) {
                        list[i].calcSalary();
                    }
                    System.out.println("Da tinh luong xong.");
                    break;
                }
                case 4: {
                    for (int i = 0; i < current; i++) {
                        list[i].displayData();
                    }
                    break;
                }
                case 5: {
                    for (int i = 0; i < current - 1; i++) {
                        for (int j = i + 1; j < current; j++) {
                            if (list[i].getSalary() < list[j].getSalary()) {
                                Employee temp = list[i];
                                list[i] = list[j];
                                list[j] = temp;
                            }
                        }
                    }
                    System.out.println("Da sap xep luong giam dan.");
                    break;
                }
                case 6: {
                    int countEmp = 0;
                    int countManager = 0;
                    for (int i = 0; i < current; i++) {
                        if (list[i] instanceof Manager) {
                            countManager++;
                        } else {
                            countEmp++;
                        }
                    }
                    System.out.println("So nhan vien: " + countEmp);
                    System.out.println("So quan ly: " + countManager);
                    break;
                }
                case 7: {
                    sc.nextLine();
                    System.out.print("Nhap ten can tim: ");
                    String key = sc.nextLine();
                    boolean found = false;
                    for (int i = 0; i < current; i++) {
                        if (list[i].getName().toLowerCase().contains(key.toLowerCase())) {
                            list[i].displayData();
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("Khong tim thay.");
                    }
                    break;
                }
            }
        } while (choice != 0);
    }
}

