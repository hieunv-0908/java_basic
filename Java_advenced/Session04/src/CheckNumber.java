public class CheckNumber {
    // Kiểm tra số chẵn
    public static boolean isEven(int a){
        return a % 2 == 0;
    }
    // Kiểm tra số lẻ
    public static boolean isOdd(int a){
        return a % 2 != 0;
    }
    // Kiểm tra số nguyên tố
    public static boolean isPrime(int a){
        if(a < 2){
            return false;
        }
        for (int i = 2; i < Math.sqrt(a); i++) {
            if(a%i==0){
                return false;
            }
        }
        return true;
    }
}
