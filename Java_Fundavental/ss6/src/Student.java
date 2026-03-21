import java.util.Date;

public class Student {
    private int msv;
    private String full_name;
    private int birth_year;
    private float avg;

    public Student(int msv, String full_name, int birth_year, float avg) {
        this.msv = msv;
        this.full_name = full_name;
        this.birth_year = birth_year;
        this.avg = avg;
    }

    public void display_student(){
        System.out.printf("MSV: %d \n Full Name : %s \n Birth Year: %d \n AVG poid: %.2f \n \n",this.msv,this.full_name,this.birth_year,this.avg);
    }
}
