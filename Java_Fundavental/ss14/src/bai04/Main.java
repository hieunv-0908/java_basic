package bai04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        List<String> sick = new ArrayList<>(List.of("Cúm A", "Sốt xuất huyết", "Cúm A", "Covid-19", "Cúm A", "Sốt xuất huyết"));
        HashMap<String,Integer> countSick = new HashMap<>();

        for (String s : sick){
            countSick.put(s,countSick.getOrDefault(s,0) + 1);
        }

        TreeMap<String,Integer> sortedCountSick = new TreeMap<>(countSick);

        System.out.println(countSick);
        System.out.println(sortedCountSick);
    }
}
