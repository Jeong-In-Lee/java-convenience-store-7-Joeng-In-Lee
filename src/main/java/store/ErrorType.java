package store;

public enum ErrorType {
    INCORRECT_PROMOTION_ERROR("[Error] 프로모션 정보가 일치하지 않습니다.");

    private final String errorMessage;


    ErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
