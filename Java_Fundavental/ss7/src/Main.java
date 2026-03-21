//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        b1();
//        b2();
//        b3();
//        b4();
//        b5();
    }

    public static void b1(){
        Student std1 = new Student("Draven");
        Student std2 = new Student("Annie");
        Student std3 = new Student("Swain");
        Student[] arrStudent = {std1,std2,std3};
        for (int i = 0; i < Student.getTotalStudent(); i++) {
            System.out.printf("SV: %d \n ID: %d \n Full Name: %s \n \n",i+1,arrStudent[i].getStudentId(),arrStudent[i].getFull_name());
        }
        Student.displayTotalStudent();
    }

    public static void b2(){
        int a = 5;
        int b =a;
        b = 20;

        Student c = new Student("c");
        Student d = c;
        d.setFull_name("d");

        System.out.println(a);
        System.out.println(c.getFull_name());

        /*
         - Biến nguyên thuỷ
        * Khi gán biến a bằng 5 rồi gán biến b bằng biến a, vì biến nguyên thuỷ vẫn có địa chỉ
        * nên ta không biết b là lấy gi trị hay lấy của a
        * nên ta gán lại b rồi in lại a. Kết quả là a không thay đổi theo b chứng minh b chỉ sao
        * chép được giá trị của a không phải địa chỉ.
        *
        * - biến không nguyên thuỷ
            phép thủe tương tự với biến nguyên thuỷ và kết quả cho ra là khi d thay đổi thì c cũng
            thay đổi , chưứng minh d là sao chép địa chỉ cuar c không phải sao chép giá trị.
        * */
    }

    public static void b3(){
        double[] scores = {6.5,8.0,4.5};
        int count = 3;
        System.out.printf("Danh sách điểm: ");
        for (int i = 0; i < count; i++) {
            System.out.printf("%.2f ",scores[i]);
        }
        System.out.printf("\n >>Kết quả xử lý: \n");
        System.out.printf("- Điểm trung bình cả lớp: %.2f \n",ScoreUtils.calculateAverage(scores));
        for (int i = 0; i < count; i++) {
            if(ScoreUtils.checkPass(scores[i])){
                System.out.println("Đạt");
            }else{
                System.out.println("Không đạt");
            }
        }
    }

    public static void b4(){
        Student std1 = new Student("Draven");
        Student std2 = new Student("Annie");
        Student std3 = new Student("Swain");
        Student[] arrStudent = {std1,std2,std3};
        int count = 3;
        for (int i = 0; i < 3; i++) arrStudent[i].payFund(20000);
        ClassRoom.showFund();
    }

//    public static void b5(){
//        Config.changeMaxScore();
//    }
}