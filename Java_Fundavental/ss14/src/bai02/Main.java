package bai02;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<String, String> medicationList = new HashMap<>();

        medicationList.put("MED001", "Paracetamol");
        medicationList.put("MED002", "Amoxicillin");
        medicationList.put("MED003", "Ibuprofen");
        medicationList.put("MED004", "Aspirin");
        medicationList.put("MED005", "Vitamin C");
        while(true){
                String code = sc.nextLine();
                if(medicationList.containsKey(code)){
                    System.out.println("Medication found: " + medicationList.get(code));
                } else {
                    System.out.println("Thuôc: " + code + " không tồn tại trong danh sách BHYT.");
                }
                break;
        }
    }
}
