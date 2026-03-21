import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MedicineManager {

    public static List<String> removeDuplicateAndSort(List<String> inputList) {

        List<String> uniqueList = new ArrayList<>();

        for (String medicine : inputList) {
            if (!uniqueList.contains(medicine)) {
                uniqueList.add(medicine);
            }
        }

        Collections.sort(uniqueList);

        return uniqueList;
    }

    public static void main(String[] args) {

        List<String> medicines = new ArrayList<>();
        medicines.add("Paracetamol");
        medicines.add("Ibuprofen");
        medicines.add("Panadol");
        medicines.add("Paracetamol");
        medicines.add("Aspirin");
        medicines.add("Ibuprofen");

        System.out.println("Input: " + medicines);

        List<String> result = removeDuplicateAndSort(medicines);

        System.out.println("Output: " + result);
    }
}