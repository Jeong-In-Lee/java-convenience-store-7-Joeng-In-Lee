package controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import model.Product;
import model.ProductManager;
import view.InputValidator;
import view.OutputView;

public class Controller {
    private final InputValidator inputValidator = new InputValidator();
    private final ProductManager productManager = new ProductManager();
    private final OutputView outputView = new OutputView();
    private final LinkedHashMap<String, Integer> cart = new LinkedHashMap<>();
    private final LinkedHashMap<String, Integer> promotionCart = new LinkedHashMap<>();
    private final LinkedHashMap<String, Integer> nonPromotionCart = new LinkedHashMap<>();
    private final LinkedHashMap<String, List<Integer>> forPromotionPrint = new LinkedHashMap<>();
    private int membershipDiscount = 0;


    public Controller() {

    }

    public void start() {
        outputView.printProduct(productManager.getProducts());
        checkExistAndQuantity();
        if (checkPromotionExist()) {
            promotionProcess();
        }
        replyNonPromotionProduct();
        checkMembership();
        receiptPrintControll();
    }

    private void receiptPrintControll() {
        outputView.printPurchasedProduct(purchasedProduct());
        printPromotionPart();
        List<Integer> money = calculateMoney();
        outputView.printMoney(money.get(0), money.get(1), money.get(2), money.get(3), money.get(4));
        restartOrNot();
    }

    private List<Integer> calculateMoney() {
        List<Integer> money = new ArrayList<>(List.of(0, 0, 0, 0, 0));
        money.set(0, cart.values().stream().mapToInt(Integer::intValue).sum());
        money.set(1, totalCost(cart));
        money.set(2, discountByPromotion());
        money.set(3, membershipDiscount * -1);
        money.set(4, totalCost(cart) - discountByPromotion() - membershipDiscount);
        return money;
    }

    private int totalCost(LinkedHashMap<String, Integer> cartToSum) {
        int sum = 0;
        for (String name : cartToSum.keySet()) {
            sum += cartToSum.get(name) * productManager.getProduct(name).getPrice();
        }
        return sum;
    }

    private int discountByPromotion() {
        int sum = 0;
        for (String name : forPromotionPrint.keySet()) {
            sum += forPromotionPrint.get(name).get(0) * productManager.getProduct(name).getPrice();
        }
        return (sum * -1);
    }

    private void printPromotionPart() {
        List<List<String>> forReceipt = makePromotionProduct();
        if (!forReceipt.isEmpty()) {
            outputView.printPromotion(forReceipt);
        }
    }

    private List<List<String>> makePromotionProduct() {
        List<List<String>> forReturn = new ArrayList<>();
        for (String productName : forPromotionPrint.keySet()) {
            if (forPromotionPrint.get(productName).get(0) != 0) {
                forReturn.add(List.of(productName, forPromotionPrint.get(productName).get(0).toString()));
            }
        }
        return forReturn;
    }

    private List<List<String>> purchasedProduct() {
        List<List<String>> forReturn = new ArrayList<>();
        for (String productName : cart.keySet()) {
            Integer cost = productManager.getProduct(productName).getPrice() * cart.get(productName);
            forReturn.add(List.of(productName, cart.get(productName).toString(), cost.toString()));
        }
        return forReturn;
    }

    private void restartOrNot() {
        if (inputValidator.getMorePurchase()) {
            resetCart();
            start();
        }
    }

    private void replyNonPromotionProduct() {
        for (String prodName : nonPromotionCart.keySet()) {
            replyOrderToProductManager(productManager.getProduct(prodName));
        }
    }

    private void checkExistAndQuantity() {
        while (true) {
            try {
                LinkedHashMap<String, Integer> orders = inputValidator.getValidatedOrder();
                checkAndAddToCart(orders);
                productManager.checkProductsQuantity(this.cart);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void checkAndAddToCart(LinkedHashMap<String, Integer> orders) {
        productManager.checkAllProductsExist(orders);
        for (String order : orders.keySet()) {
            cart.put(order, orders.get(order));
        }
    }

    private boolean checkPromotionExist() {
        for (String order : this.cart.keySet()) {
            if (productManager.checkProductPromotionExist(order)) {
                this.promotionCart.put(order, this.cart.get(order));
                continue;
            }
            this.nonPromotionCart.put(order, this.cart.get(order));
        }
        return (!this.promotionCart.isEmpty());
    }

    // 양 감당 가능 프로모션 양만큼 가져왔는지 check || 양 적을 때
    private void promotionProcess() {
        for (String promotionProductName : this.promotionCart.keySet()) {
            if (productManager.orderIsBiggerThanPromotionQuantity(promotionProductName, //주문 > 프로모션재고
                    this.promotionCart.get(promotionProductName))) {
                whenOrderIsBiggerThanPromotionQuantity(productManager.getProduct(promotionProductName));
                continue;
            }
            whenOrderIsLessThanPromotionQuantity(productManager.getProduct(promotionProductName)); //주문량<= 프로모션 재고
        }
    }


    private void whenOrderIsBiggerThanPromotionQuantity(Product prod) {
        List<Integer> promotionApply = prod.getPromotion().applyPromotion(prod.getQuantity());
        promotionApply.set(2,
                (cart.get(prod.getName()) - promotionApply.get(0) * (prod.getPromotion().getSet()) - promotionApply.get(
                        1)));
        if (!inputValidator.getBuyWithoutPromotion(prod.getName(), promotionApply.get(1) + promotionApply.get(2))) {
            promotionApply = new ArrayList<>(List.of(promotionApply.get(0), 0, 0));
        }
        replyOrderToProductPair(getPairProducts(prod), promotionApply);
    }

    private void replyOrderToProductPair(List<Product> pair, List<Integer> promotionApply) {
        Product p1 = pair.get(0);
        Product p2 = pair.get(1);
        forPromotionPrint.put(p1.getName(), promotionApply);

        p1.setQuantity(p1.getQuantity() - forPromotionPrint.get(p1.getName()).get(0) * p1.getPromotion().getSet()
                - forPromotionPrint.get(p1.getName()).get(1));
        p2.setQuantity(p2.getQuantity() - forPromotionPrint.get(p2.getName()).get(2));
    }

    private List<Product> getPairProducts(Product prod) {
        return productManager.getProducts().stream()
                .filter(product -> product.getName().equals(prod.getName()))
                .sorted((p1, p2) -> Boolean.compare(p2.getIsPromotion(), p1.getIsPromotion())).toList();
    }

    //주문량 <= 프로모션 재고 || 첫번째 if 프로모션인데 안들고 옴 && 증정해도 재고수량 안넘는가? || 두번째 if 증정 받을지 사용자 input
    private void whenOrderIsLessThanPromotionQuantity(Product prod) {
        List<Integer> promotionApply = prod.getPromotion().applyPromotion(this.promotionCart.get(prod.getName()));
        promotionApply = checkPromotionEnough(prod, promotionApply);
        forPromotionPrint.put(prod.getName(), promotionApply);
        replyOrderToProductManager(prod);
    }

    private List<Integer> checkPromotionEnough(Product prod, List<Integer> promotionApply) {
        if (promotionApply.get(1).equals(prod.getPromotion().getBuy()) && (
                promotionCart.get(prod.getName()) + prod.getPromotion().getGet() <= prod.getQuantity())) {
            if (inputValidator.getMorePromotion(prod.getName(), prod.getPromotion().getGet())) {
                promotionApply = new ArrayList<>(List.of((promotionApply.get(0) + 1), 0, 0));
                updateAllCart(prod);
            }
        }
        return promotionApply;
    }


    private void updateAllCart(Product prod) {
        cart.put(prod.getName(), cart.get(prod.getName()) + prod.getPromotion().getGet());
        promotionCart.put(prod.getName(), cart.get(prod.getName()) + prod.getPromotion().getGet());
    }

    private void replyOrderToProductManager(Product prod) {
        prod.setQuantity(prod.getQuantity() - cart.get(prod.getName()));
    }

    private void checkMembership() {
        int sum = totalCost(nonPromotionCart);
        membershipDiscount = (int) (sum * 0.3);
    }

    private void resetCart() {
        cart.clear();
        promotionCart.clear();
        nonPromotionCart.clear();
        forPromotionPrint.clear();
    }

}
