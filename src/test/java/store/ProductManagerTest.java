package store;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import model.ProductManager;
import org.junit.jupiter.api.Test;

public class ProductManagerTest {
    private ProductManager productManager = new ProductManager();

    @Test
    public void 존재하지_않는_상품() {
        LinkedHashMap<String, Integer> testOrder = new LinkedHashMap<>();
        testOrder.put("제로콜라", 10);
        assertThatThrownBy(() -> productManager.checkAllProductsExist(testOrder)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 더_많은_수량을_주문() {
        LinkedHashMap<String, Integer> testOrder = new LinkedHashMap<>();
        testOrder.put("콜라", 35);
        assertThatThrownBy(() -> productManager.checkProductsQuantity(testOrder)).isInstanceOf(IllegalArgumentException.class);
    }
}
