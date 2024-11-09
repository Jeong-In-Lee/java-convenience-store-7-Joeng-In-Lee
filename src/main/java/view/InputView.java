package view;

public class InputView {

    public String getOrder() {
        System.out.println("\n구매하실 상품명과 수량을 입력해주세요. (예: [사이다-2],[갑자칩-1])");
        return System.console().readLine();
    }

    public String askMorePromotion(String productName, int moreQuantity) {
        System.out.println("현재 " + productName + "은(는) " + moreQuantity + "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        return System.console().readLine();
    }

    public String askBuyWithoutPromotion(String productName, int moreQuantity) {
        System.out.println("현재 " + productName + " " + moreQuantity + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        return System.console().readLine();
    }

    public String askMembershipDiscount() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return System.console().readLine();
    }

    public String askMorePurchase() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return System.console().readLine();
    }
}
