package controller;

import java.util.HashMap;
import java.util.Map;
import model.ControllerValidator;
import model.Product;
import model.ProductManager;
import view.InputValidator;
import view.OutputView;

public class Controller {
    private final InputValidator inputValidator = new InputValidator();
    private final ProductManager productManager = new ProductManager();
    private final OutputView outputView = new OutputView();
//    private final ControllerValidator controllerValidator = new ControllerValidator();
    private HashMap<String, Integer> cart = new HashMap<>();

    public Controller() {

    }

    public void start() {
        // 상품 정보 출력
        checkAndAddToCart();


    }

    private void checkAndAddToCart() {
        while (true) {
            try {
                addToCart(productManager.checkAllProductsExist(inputValidator.getValidatedOrder()));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void addToCart(HashMap<String, Integer> orders) {
        for (String order : orders.keySet()){
            cart.put(order, orders.get(order));
        }
    }


}
