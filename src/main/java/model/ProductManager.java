package model;

import static store.ErrorType.NO_PRODUCT_EXIST;
import static store.ErrorType.OVER_THAN_PRODUCT_QUANTITY;

import java.util.LinkedHashMap;
import java.util.List;

public class ProductManager {
    private final ReadMarkdown readMarkdown = new ReadMarkdown();
    private List<Product> products;

    public ProductManager() {
        try {
            this.products = readMarkdown.makeProductList();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void checkAllProductsExist(LinkedHashMap<String, Integer> orders) {
        for (String productName : orders.keySet()) {
            if (!checkProductExist(productName)) {
                throw new IllegalArgumentException(NO_PRODUCT_EXIST.getErrorMessage());
            }
        }
    }

    private boolean checkProductExist(String productName) {
        for (Product product : products) {
            if (product.isSameProduct(productName)) {
                return true;
            }
        }
        return false;
    }

    public void checkProductsQuantity(LinkedHashMap<String, Integer> cart) {
        for (String productName : cart.keySet()) {
            if (getProductTotalQuantity(productName) < cart.get(productName)) {
                throw new IllegalArgumentException(OVER_THAN_PRODUCT_QUANTITY.getErrorMessage());
            }
        }
    }

    private int getProductTotalQuantity(String productName) {
        int sum = 0;
        for (Product product : products) {
            if (product.isSameProduct(productName)) {
                sum += product.getQuantity();
            }
        }
        return sum;
    }

    public boolean checkProductPromotionExist(String productName) {
        for (Product product : products) {
            if (product.isSameProduct(productName)) {
                return product.getIsPromotion();
            }
        }
        throw new IllegalStateException("[ERROR] Product should exist in the list 1 "); // return 처리하기 위함
    }

    public boolean orderIsBiggerThanPromotionQuantity(String productName, int orderQuantity) {
        for (Product product : products) {
            if (product.isSameProduct(productName) && product.getIsPromotion()) {
                return (product.getQuantity() <= orderQuantity);
            }
        }
        return false;
    }

    public Product getProduct(String productName) {
        for (Product product : products) {
            if (product.isSameProduct(productName)) {
                return product;
            }
        }
        throw new IllegalStateException("[ERROR] Product should exist in the list 2"); // return 처리하기 위함
    }


}
