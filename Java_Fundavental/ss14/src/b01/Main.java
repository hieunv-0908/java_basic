package b01;

import java.util.*;
public class Main {
    public static void main(String[] args) {
        HashSet<Patient> setPatients = new HashSet();
        LinkedHashSet<Patient> linkedHashSet = new LinkedHashSet();

        setPatients.add(new Patient("Nguyễn Văn A", "Yên Bái"));
        setPatients.add(new Patient("Trần Thị B", "Thai Bình"));
        setPatients.add(new Patient("Lê Văn C", "Hưng Yên"));
        setPatients.add(new Patient("Nguyễn Văn A", "Yên Bái"));

        linkedHashSet.add(new Patient("Nguyễn Văn A", "Yên Bái"));
        linkedHashSet.add(new Patient("Trần Thị B", "Thai Bình"));
        linkedHashSet.add(new Patient("Lê Văn C", "Hưng Yên"));
        linkedHashSet.add(new Patient("Nguyễn Văn A", "Yên Bái"));

        System.out.println(setPatients);
        System.out.println(linkedHashSet);
    }
}