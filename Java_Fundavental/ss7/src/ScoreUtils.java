public class ScoreUtils {
    public static boolean checkPass(double score){
        if(score >= 5){
            System.out.printf("- Điểm %.2f: ", score);
            return true;
        }else{
            System.out.printf("- Điểm %.2f: ", score);
            return false;
        }
    }

    public static double calculateAverage(double[] scores){
        double totalScore = 0;
        int count = 0;
        for (int i = 0; i < scores.length; i++) {
            if(scores[i] != 0.0){
                totalScore += scores[i];
                count++;
            }
        }
        return totalScore/count;
    }
}
