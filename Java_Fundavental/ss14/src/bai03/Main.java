package bai03;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        HashSet<String> A = new HashSet<>();
        HashSet<String> B = new HashSet<>();

        A.add("Aspirin");
        A.add("Caffeine");
        A.add("Paracetamol");

        B.add("Penicillin");
        B.add("Aspirin");
        B.add("Pollen");

        HashSet<String> retain = new HashSet<>(A);
        HashSet<String> remove = new HashSet<>(A);

        if(retain.retainAll(B)){
            System.out.println("Bệnh nhân B có dị ứng với thành phần của thuốc A.");
            remove.removeAll(B);
            System.out.println("Thành phần thuốc mà bệnh nhân không bị dị ứng là: " + remove);
        } else {
            System.out.println("Bệnh nhân B không dị ứng với tất cả thành phần của thuốc A.");
        }
    }
}
