package bai05;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        User user = new User();

        System.out.println("Nhập tuổi của người dùng:");

        try {
            int age = Integer.parseInt(sc.nextLine());
            user.setAge(age);
            System.out.println("Đăng ký thành công! Tuổi: " + user.getAge());
        } catch (InvalidAgeException e) {
            System.out.println("Lỗi nghiệp vụ: " + e.getMessage());
        } catch (NumberFormatException e){
            System.out.println("Tuổi phải là số!");

        }
    }
}
