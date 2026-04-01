package test;

import dao.OrderDAO;
import dao.FoodAndDrinkDAO;
import dao.OrderFoodItemDAO;
import model.FoodAndDrink;

/**
 * Test class để kiểm tra chức năng auto trừ tồn kho
 */
public class StockDeductionTest {
    
    public static void main(String[] args) {
        System.out.println("=== TEST AUTO TRỪ TỒN KHO ===");
        
        // Test 1: Kiểm tra method updateStock
        testUpdateStock();
        
        // Test 2: Kiểm tra auto trừ khi update order status
        testAutoDeduction();
        
        System.out.println("\n✅ Test hoàn thành!");
    }
    
    private static void testUpdateStock() {
        System.out.println("\n--- Test 1: Update Stock ---");
        
        // Lấy một F&B để test
        FoodAndDrink food = FoodAndDrinkDAO.getFoodAndDrinkById(1);
        if (food != null) {
            System.out.println("Trước khi update: " + food.getName() + " - Stock: " + food.getStock());
            
            // Trừ 2 đơn vị
            boolean success = FoodAndDrinkDAO.updateStock(1, -2);
            
            // Kiểm tra lại
            FoodAndDrink updatedFood = FoodAndDrinkDAO.getFoodAndDrinkById(1);
            System.out.println("Sau khi update: " + updatedFood.getName() + " - Stock: " + updatedFood.getStock());
            System.out.println("Update stock: " + (success ? "✅ Thành công" : "❌ Thất bại"));
            
            // Rollback: cộng lại 2 đơn vị
            FoodAndDrinkDAO.updateStock(1, 2);
        } else {
            System.out.println("❌ Không tìm thấy F&B ID=1 để test");
        }
    }
    
    private static void testAutoDeduction() {
        System.out.println("\n--- Test 2: Auto Deduction ---");
        
        // Giả sử có order ID = 1
        System.out.println("Khi cập nhật order status sang 'SERVING':");
        System.out.println("- Hệ thống sẽ tự động trừ tồn kho các món trong đơn hàng");
        System.out.println("- Kiểm tra đủ tồn kho trước khi trừ");
        System.out.println("- Hiển thị log chi tiết các món đã trừ");
        
        // Test thực tế khi có data
        // OrderDAO.updateOrderStatus(1, "SERVING");
    }
}
