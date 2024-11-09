package store;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import view.InputValidator;

public class InputValidationTest {

    private InputValidator inputValidator = new InputValidator();

    @ParameterizedTest
    @NullAndEmptySource
    void 주문_널_체크(String input) {
        assertThatThrownBy(() -> inputValidator.validateInput(input))
                .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void yesNo_널_체크(String input) {
        assertThatThrownBy(() -> inputValidator.checkFormatYesNo(input))
                .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"[콜라-3],[사이다-1];사이다", "[물-2];물"}, delimiter = ';')
    void 주문_입력_정상_작동(String input, String expectedKey) {
        assertTrue(inputValidator.validateInput(input).containsKey(expectedKey));
    }

    @ParameterizedTest
    @CsvSource(value = {"[콜라-3][사이다-1]", "sd", "[][", "{콜라-1}", "[콜라-0]"}, delimiter = ';')
    void 허용되지_않은_형식의_주문_입력(String input) {
        assertThatThrownBy(() -> inputValidator.validateInput(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"y,true", "Y,true", "n,false", "N,false"})
    void YesNo_정상_작동(String input, boolean expectedBoolean) {
        assertEquals(inputValidator.checkFormatYesNo(input), expectedBoolean);
    }
}
