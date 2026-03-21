package bai03;

public class Main {
    public static void main(String[] args) {

        MedicationProcessChecker checker = new MedicationProcessChecker();

        String[] actions1 = {"PUSH", "PUSH", "POP", "POP"};
        checker.checkProcess(actions1);

        checker.reset();

        String[] actions2 = {"POP", "PUSH"};
        checker.checkProcess(actions2);

        checker.reset();

        String[] actions3 = {"PUSH", "PUSH", "POP"};
        checker.checkProcess(actions3);
    }
}
