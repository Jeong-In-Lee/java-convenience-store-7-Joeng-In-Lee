package model;

import java.util.List;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private Boolean isPromotion = false;
    private Promotion promotion = null;

    public Product(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(String name, int price, int quantity, List<String> promotionInfo) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = new Promotion(promotionInfo);
        if (this.promotion.checkDate()) {
            this.isPromotion = true;
        }
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getPrice() {
        return this.price;
    }

    public Boolean getIsPromotion() {
        return this.isPromotion;
    }

    public Promotion getPromotion() {
        return this.promotion;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isSameProduct(String name) {
        return (this.name.equals(name));
    }
}
