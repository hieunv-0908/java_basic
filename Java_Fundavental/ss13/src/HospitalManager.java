import java.util.ArrayList;
import java.util.List;

public class HospitalManager {

    public static <T> List<T> findCommonPatients(List<T> listA, List<T> listB) {

        List<T> commonList = new ArrayList<>();

        for (T item : listA) {
            if (listB.contains(item) && !commonList.contains(item)) {
                commonList.add(item);
            }
        }

        return commonList;
    }

    public static void main(String[] args) {

        // ===== Test Case 1 (Integer) =====
        List<Integer> listIntA = new ArrayList<>();
        listIntA.add(101);
        listIntA.add(102);
        listIntA.add(105);

        List<Integer> listIntB = new ArrayList<>();
        listIntB.add(102);
        listIntB.add(105);
        listIntB.add(108);

        List<Integer> resultInt = findCommonPatients(listIntA, listIntB);
        System.out.println("Test Case 1 Output: " + resultInt);


        // ===== Test Case 2 (String) =====
        List<String> listStrA = new ArrayList<>();
        listStrA.add("DN01");
        listStrA.add("DN02");
        listStrA.add("DN03");

        List<String> listStrB = new ArrayList<>();
        listStrB.add("DN02");
        listStrB.add("DN04");

        List<String> resultStr = findCommonPatients(listStrA, listStrB);
        System.out.println("Test Case 2 Output: " + resultStr);
    }
}