import java.util.ArrayList;
import java.util.Optional;

public class ProductManager {

    private ArrayList<Product> productList = new ArrayList<>();

    public void addProduct(Product product) throws InvalidProductException {

        boolean isExist = productList.stream()
                .anyMatch(p -> p.getId() == product.getId());

        if (isExist) {
            throw new InvalidProductException("ID đã tồn tại!");
        }

        productList.add(product);
        System.out.println("Thêm sản phẩm thành công!");
    }

    public void displayProducts() {

        if (productList.isEmpty()) {
            System.out.println("Danh sách trống");
            return;
        }

        System.out.printf("ID", "Name", "Price", "Qty", "Category");

        productList.forEach(p -> {
            System.out.printf("%d %s %2f %d %s\n",
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getQuantity(),
                    p.getCategory());
        });
    }

    public void updateQuantity(int id, int newQuantity) throws InvalidProductException {

        Optional<Product> productOptional = productList.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (productOptional.isPresent()) {
            productOptional.get().setQuantity(newQuantity);
            System.out.println("Cập nhật thành công!");
        } else {
            throw new InvalidProductException("Không tìm thấy sản phẩm!");
        }
    }

    public void deleteOutOfStock() {

        productList.removeIf(p -> p.getQuantity() == 0);

        System.out.println("Đã xóa các sản phẩm hết hàng!");
    }

}