public class Student {
    private int studentId;
    private String full_name;
    private static int totalStudent = 0;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public static int getTotalStudent() {
        return totalStudent;
    }

    public static void setTotalStudent(int totalStudent) {
        Student.totalStudent = totalStudent;
    }

    public Student(String full_name) {
        totalStudent++;
        this.studentId = totalStudent;
        this.full_name = full_name;
    }

    public void displayInfo(){
        System.out.println("Thông tin của sinh viên.");
        System.out.println("ID: " + this.studentId);
        System.out.println("Full Name: " + this.full_name);
    }

    public static void displayTotalStudent(){
        System.out.println("Tổng số sinh viên: " + totalStudent);
    }

    public void payFund(double money){
        ClassRoom.addFund(money);
    }

    public void viewFound(){
        ClassRoom.showFund();
    }
}