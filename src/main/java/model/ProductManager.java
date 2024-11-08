package model;

import static store.ErrorType.NO_PRODUCT_EXIST;

import java.util.HashMap;
import java.util.List;

public class ProductManager {
    private final ReadMarkdown readMarkdown = new ReadMarkdown();
    private List<Product> products;

    public ProductManager() {
        this.products = readMarkdown.makeProductList();
    }

    public List<Product> getProducts() {
        return products;
    }

    public HashMap<String, Integer> checkAllProductsExist(HashMap<String, Integer> order) {
        for (String productName : order.keySet()) {
            if (!checkProductExist(productName)){
                throw new IllegalArgumentException(NO_PRODUCT_EXIST.getErrorMessage());
            }
        }
        return order;
    }

    private boolean checkProductExist(String productName) {
        for (Product product : products) {
            if (product.isSameProduct(productName)){
                return true;
            }
        }
        return false;
    }
}
