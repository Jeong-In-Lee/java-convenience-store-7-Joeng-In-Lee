package store;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import model.Product;
import model.Promotion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ProductTest {

    @Test
    public void 생성자_정상_동작() {
        Product testWithOutPromotion = new Product("콜라", 1000, 10);
        Product testWithPromotion = new Product("콜라", 1000, 10, List.of("MD추천상품", "1", "1", "2024-01-01", "2024-12-31"));

        assertEquals(testWithOutPromotion.getIsPromotion(), false);
        assertEquals(testWithPromotion.getIsPromotion(), true);
    }

    @ParameterizedTest
    @CsvSource({"콜라,콜라", "컵라면,컵라면","물,물"})
    void 상품명_비교_정상_동작(String input, String expectedOutput) {
        Product testWithOutPromotion = new Product(input, 1000, 10);
        assertTrue(testWithOutPromotion.isSameProduct(expectedOutput));
    }
}
