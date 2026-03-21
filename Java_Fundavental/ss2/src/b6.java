import java.util.Scanner;

public class b6 {
    public static void main(String[] args){
        int max = 0;
        int min = Integer.MAX_VALUE;
        int total = 0;
        int avg;
        int temp;
        int count = 0;
        Scanner sc = new Scanner(System.in);
        for (int i = 1 ; i <= 7; i++){
            if(i < 7){
                System.out.printf("Nhap so sach muon thu %d: ",i+1);
            }else{
                System.out.printf("Nhap vao chu nhat: ");
            }
            temp = Integer.parseInt(sc.nextLine());
            if(temp == 0) continue;
            if(temp > max) max = temp;
            if(temp < min) min = temp;
            total += temp;
            count++;
        }
        avg = total / count;
        System.out.printf("Luot muon cao nhat: %d",max);
        System.out.printf("Luot muon thap nhat: %d",min);
        System.out.printf("Luot muon trung binh: %d",avg);

    }
}
