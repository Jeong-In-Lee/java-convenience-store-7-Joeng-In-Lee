package view;

import static store.ErrorType.INPUT_ERROR;
import static store.ErrorType.INPUT_TYPE_ERROR;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Product;
import store.ErrorType;

public class InputValidator {
    private final InputView inputView = new InputView();
    private static final String ITEM_PATTERN = "\\[([a-zA-Z가-힣]+)-([1-9]\\d*)\\]";

    public LinkedHashMap<String, Integer> getValidatedOrder() {
        while (true) {
            try {
                String orders = inputView.getOrder();
                return validateInput(orders);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public LinkedHashMap<String, Integer> validateInput(String orders){
        nullCheck(orders);
        typeCheck(orders);
        return makeOrderMap(orders);
    }

    private void nullCheck(String orders) {
        if (orders.isEmpty()) {
            throw new NullPointerException(INPUT_ERROR.getErrorMessage());
        }
    }

    private void typeCheck(String orders) {
        if (!orders.matches("(" + ITEM_PATTERN + ")(,(" + ITEM_PATTERN + "))*")) {
            throw new IllegalArgumentException(INPUT_TYPE_ERROR.getErrorMessage());
        }
    }

    private LinkedHashMap<String, Integer> makeOrderMap(String orders) {
        LinkedHashMap<String, Integer> temp = new LinkedHashMap<>();
        Pattern pattern = Pattern.compile(ITEM_PATTERN);
        Matcher matcher = pattern.matcher(orders);
        while (matcher.find()) {
            temp.put(matcher.group(1), Integer.parseInt(matcher.group(2)));
        }
        return temp;
    }

    public boolean getMorePromotion(String productName, int moreQuantity) {
        while (true) {
            try {
                String answer = inputView.askMorePromotion(productName, moreQuantity);
                return checkFormatYesNo(answer);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean checkFormatYesNo(String answer) {
        nullCheck(answer);
        if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("N")) {
            return answer.equalsIgnoreCase("Y");
        }
        throw new IllegalArgumentException(INPUT_ERROR.getErrorMessage());
    }

    public boolean getBuyWithoutPromotion(String productName, int moreQuantity) {
        while (true) {
            try {
                String answer = inputView.askBuyWithoutPromotion(productName, moreQuantity);
                return checkFormatYesNo(answer);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean getMembershipDiscount() {
        while (true) {
            try {
                String answer = inputView.askMembershipDiscount();
                return checkFormatYesNo(answer);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean getMorePurchase() {
        while (true) {
            try {
                String answer = inputView.askMorePurchase();
                return checkFormatYesNo(answer);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
