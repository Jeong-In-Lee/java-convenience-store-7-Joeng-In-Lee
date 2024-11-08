package store;

public enum ErrorType {
    INCORRECT_PROMOTION_ERROR("[Error] 프로모션 정보가 일치하지 않습니다."),
    NO_PRODUCT_EXIST("[ERROR] [ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    OVER_THAN_PRODUCT_QUANTITY("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");

    private final String errorMessage;


    ErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
