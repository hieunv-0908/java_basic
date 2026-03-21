import java.util.*;

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student(18, "An", "0901234567", true);
        Student s2 = new Student(19, "Binh", "0912345678", false);
        Student s3 = new Student(20, "Cuong", "0923456789", true);
        Student s4 = new Student(21, "Dung", "0934567890", true);
        Student s5 = new Student(22, "Hanh", "0945678901", false);
        Student s6 = new Student(18, "Lan", "0956789012", true);
        Student s7 = new Student(23, "Minh", "0967890123", false);
        Student s8 = new Student(20, "Ngoc", "0978901234", true);
        Student s9 = new Student(24, "Phuong", "0989012345", true);
        Student s10 = new Student(19, "Tuan", "0990123456", false);
        List<Student> studentAccounts = new ArrayList<>(List.of(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10));
        Comparator<Student> ampAge = (a,b)-> a.age-b.age;
        Collections.sort(studentAccounts,ampAge);
        for (Student s: studentAccounts){
            System.out.println(s.name + " " + s.age);
        }
    }
}