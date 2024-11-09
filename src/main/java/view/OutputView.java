package view;

import java.util.List;
import model.Product;

public class OutputView {
    public void printProduct(List<Product> products) {
        System.out.println("안녕하세요. w편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
        for (Product product : products) {
            System.out.println(
                    "- " + product.getName() + " " + String.format("%,d", product.getPrice()) + "원 " + String.format("%,d",
                            product.getQuantity()) + "개 " + printPromotionOrNot(product));
        }
    }

    private String printPromotionOrNot(Product product) {
        if (product.getIsPromotion()){
            return product.getPromotion().getName();
        }
        return "";
    }

    public void printPurchasedProduct(List<List<String>> list) {
        System.out.printf("%sW 편의점%s\n", "=".repeat(20), "=".repeat(20));
        for (List<String> printString : list) {
            System.out.printf("%-20s %-10s %10s\n", printString.get(0),
                    String.format("%,d", Integer.parseInt(printString.get(1))),
                    String.format("%,d", Integer.parseInt(printString.get(2))));
        }
    }

    public void printPromotion(List<List<String>> list) {
        System.out.printf("%s증    정%s\n", "=".repeat(20), "=".repeat(20));
        for (List<String> printString : list) {
            System.out.printf("%-20s %-10s\n", printString.get(0),
                    String.format("%,d", Integer.parseInt(printString.get(1))));
        }
    }

    public void printMoney(int totCount, int totMoney, int promotionMoney, int membershipMoney, int purchaseMoney) {
        System.out.printf("%s\n", "=".repeat(45));
        System.out.printf("%-20s %-10s %10s\n", "총구매액", String.format("%,d", totCount), String.format("%,d", totMoney));
        System.out.printf("%-31s -%9s\n", "행사할인", String.format("%,d", promotionMoney));
        System.out.printf("%-31s -%9s\n", "멤버십할인", String.format("%,d", membershipMoney));
        System.out.printf("%-30s %10s\n", "내실돈", String.format("%,d", purchaseMoney));
    }
}
