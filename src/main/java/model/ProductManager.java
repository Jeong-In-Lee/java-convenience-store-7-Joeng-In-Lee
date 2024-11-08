package model;

import static store.ErrorType.NO_PRODUCT_EXIST;
import static store.ErrorType.OVER_THAN_PRODUCT_QUANTITY;

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

    public void checkAllProductsExist(HashMap<String, Integer> orders) {
        for (String productName : orders.keySet()) {
            if (!checkProductExist(productName)){
                throw new IllegalArgumentException(NO_PRODUCT_EXIST.getErrorMessage());
            }
        }
    }

    private boolean checkProductExist(String productName) {
        for (Product product : products) {
            if (product.isSameProduct(productName)){
                return true;
            }
        }
        return false;
    }

    public void checkProductsQuantity(HashMap<String, Integer> cart) {
        for (String productName : cart.keySet()) {
            if (getProductTotalQuantity(productName) < cart.get(productName)){
                throw new IllegalArgumentException(OVER_THAN_PRODUCT_QUANTITY.getErrorMessage());
            }
        }
    }

    private int getProductTotalQuantity(String productName) {
        int sum = 0;
        for (Product product : products) {
            if (product.isSameProduct(productName)){
                sum += product.getQuantity();
            }
        }
        return sum;
    }



}
