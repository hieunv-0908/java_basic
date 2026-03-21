public class Caculate {
    // add
    public static int add(int a, int b){
        return a+b;
    }

    // minus
    public static int minus(int a, int b){
        return a-b;
    }

    // multi
    public static int multi(int a, int b){
        return a*b;
    }

    // devide
    public static double devide(int a, int b){
        if(b==0){
            throw new RuntimeException("Can not devide by zero");
        }
        return (double) a/b;
    }
}
