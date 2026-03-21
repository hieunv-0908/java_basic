public class ClassRoom {
    private static double classFund;

    public static void addFund(double money){
        classFund += money;
        System.out.println("Đóng thành công");
    }

    public static void showFund(){
        System.out.println("Quỹ lớp: " + classFund);
    }
}
