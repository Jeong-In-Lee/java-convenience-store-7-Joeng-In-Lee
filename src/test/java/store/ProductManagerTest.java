package store;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import model.ProductManager;
import org.junit.jupiter.api.Test;

public class ProductManagerTest {
    private ProductManager productManager = new ProductManager();

    @Test
    public void 존재하는_상품인지_확인() {
        HashMap<String, Integer> testOrder = new HashMap<>();
        testOrder.put("콜라", 10);
        testOrder.put("감자칩", 3);
        testOrder.put("정식도시락", 1);
        assertTrue(productManager.checkAllProductsExist(testOrder).containsKey("콜라"), "'콜라' 상품이 존재해야 합니다.");
        assertTrue(productManager.checkAllProductsExist(testOrder).containsKey("감자칩"), "'감자칩' 상품이 존재해야 합니다.");
        assertTrue(productManager.checkAllProductsExist(testOrder).containsKey("정식도시락"), "'정식도시락' 상품이 존재해야 합니다.");
    }

    @Test
    public void 존재하지_않는_상품() {
        HashMap<String, Integer> testOrder = new HashMap<>();
        testOrder.put("제로콜라", 10);
        assertThatThrownBy(() -> productManager.checkAllProductsExist(testOrder)).isInstanceOf(IllegalArgumentException.class);
    }
}
