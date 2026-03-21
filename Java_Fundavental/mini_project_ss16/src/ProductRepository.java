import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository implements IRepository<Product>{
    ArrayList<Product> productsList = new ArrayList<>();
    HashMap<String, Product> productMap = new HashMap<>();

    @Override
    public boolean add(Product item) {
            if (item == null || productMap.containsKey(item.id)) {
                return false;
            }else {
                productsList.add(item);
                productMap.put(item.id, item);
                return true;
            }
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }

    @Override
    public Product findById(String id) {
        productMap.get(id).displayInfo();
        return null;
    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    public Map<String, Integer> countProductsType() {
        Map<String, Integer> countMap = new HashMap<>();
        for (Product product : productsList) {
            String typeName = product.getClass().getSimpleName();
            countMap.put(typeName, countMap.getOrDefault(typeName, 0) + 1);
        }
        return countMap;
    }

    public void sortPriceAscending() {
        productsList.sort((p1, p2) -> Double.compare(p1.price, p2.price));
    }

    public void displayAllProducts() {
        for (Product product : productsList) {
            product.displayInfo();
            System.out.println("Thành tiền: " + product.calculateFinalPrice());
            System.out.println("-------------------------");
        }
    }
}
