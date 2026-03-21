package bai01;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Double> list = new ArrayList<>(); // lưu danh sách phí vận chuyển
        Set<Double> set = new HashSet<>(); // lưu danh sách ức phí vận chuyển khác nhau
        System.out.println("Mời nhập vào số lượng đơn hàng: ");
        int N = Integer.parseInt(sc.nextLine());
        if(N == 0){
            System.out.println("Lỗi: Số đơn hàng không hợp lệ!");
            return;
        }
        for (int i = 0; i < N; i++) {
            System.out.println("Mời nhập vào phí vận chuyển cho đơn hàng " + (i + 1) + ": ");
            double fee = Double.parseDouble(sc.nextLine());
            list.add(fee);
            set.add(fee);
        }
        set.addAll(list);
        FindLowestHighest(list, set);
        TotalRevenueAverage(list, set);
        FeeListUniqueDescending(list, set);
        CountExpress(list, set);
    }

    //a. Phương thức tìm mức phí vận chuyển thấp nhất và cao nhất
    public static void FindLowestHighest(List<Double> list, Set<Double> set) {
            // Tìm mức phí thấp nhất và cao nhất
            double minFee = Collections.min(set);
            double maxFee = Collections.max(set);

            System.out.println("Min: " + minFee);
            System.out.println("Max: " + maxFee);
    }

    //b. Tính tổng duanh thu phí vận chuyển và phí trung bình mỗi đơn
    public static void TotalRevenueAverage(List<Double> list, Set<Double> set) {
        double totalRevenue = 0.0;
        for (double fee : list) {
            totalRevenue += fee;
        }
        double averageFee = totalRevenue / list.size();


        System.out.println("Average: " + averageFee);
    }

    //c. Hiển thị danh sách mức phí duy nhất giảm dần
    public static void FeeListUniqueDescending(List<Double> list, Set<Double> set){
        // Chuyển set thành list để sắp xếp
        List<Double> uniqueFees = new ArrayList<>(set);
        Collections.sort(uniqueFees, Collections.reverseOrder()); // Sắp xếp giảm dần

        System.out.println("Unique (Unique): " + uniqueFees);
    }

    //d. Đếm số lượng đn hàng có phí Hoả tốc phí >= 50
    public static void CountExpress(List<Double> list, Set<Double> set){
            int count = 0;
            for (double fee : list) {
                if (fee >= 50) {
                    count++;
                }
            }

            System.out.println("Express: " + count);
    }
}

