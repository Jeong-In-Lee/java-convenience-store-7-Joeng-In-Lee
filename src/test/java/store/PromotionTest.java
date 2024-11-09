package store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import model.Promotion;
import org.junit.jupiter.api.Test;

public class PromotionTest {

    @Test
    void 상품명_비교_정상_동작() {
        Promotion testPromotion = new Promotion(List.of("MD추천상품", "1", "1", "2024-01-01", "2024-12-31"));
        Promotion testPromotion2 = new Promotion(List.of("MD추천상품", "1", "1", "2023-01-01", "2023-12-31"));

        assertTrue(testPromotion.checkDate());
        assertFalse(testPromotion2.checkDate());
    }

    @Test
    void 프로모션_계산_정상_작동() {
        Promotion testPromotion = new Promotion(List.of("MD추천상품", "2", "1", "2024-01-01", "2024-12-31"));

        assertEquals(testPromotion.applyPromotion(4).get(0), 1);
        assertEquals(testPromotion.applyPromotion(4).get(1), 1);
    }
}
