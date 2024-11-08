package controller;

import java.util.HashMap;
import model.ProductManager;
import view.InputValidator;
import view.OutputView;

public class Controller {
    private final InputValidator inputValidator = new InputValidator();
    private final ProductManager productManager = new ProductManager();
    private final OutputView outputView = new OutputView();
    private HashMap<String, Integer> cart = new HashMap<>();
    private HashMap<String, Integer> promotionCart = new HashMap<>();
    private HashMap<String, Integer> nonPromotionCart = new HashMap<>();


    public Controller() {

    }

    public void start() {
        // 상품 정보 출력
        checkExistAndQuantity();
        if (checkPromotionExist()) {
            promotionProcess();
        }
        checkMembership();
    }

    private void checkExistAndQuantity() {
        while(true) {
            try{
                HashMap<String, Integer> orders = inputValidator.getValidatedOrder();
                checkAndAddToCart(orders);
                productManager.checkProductsQuantity(this.cart);
                break;
            } catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
        }
    }

    private void checkAndAddToCart(HashMap<String, Integer> orders) {
        productManager.checkAllProductsExist(orders);
        for (String order : orders.keySet()){
            cart.put(order, orders.get(order));
        }
    }

    private boolean checkPromotionExist() {
        for (String order : this.cart.keySet()){
            if(productManager.checkProductPromotionExist(order)){
                this.promotionCart.put(order, this.cart.get(order));
                continue;
            }
            this.nonPromotionCart.put(order, this.cart.get(order));
        }
        return (!this.promotionCart.isEmpty());
    }

    private void promotionProcess() {

    }

    private void checkMembership() {

    }

}
