package store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import model.Product;
import model.ReadMarkdown;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReadMarkdownTest {
    private ReadMarkdown readMarkdown;

    @BeforeEach
    public void setUp() {
        readMarkdown = new ReadMarkdown();
    }

    @Test
    public void 상품리스트_파싱_확인() {
        List<Product> testProducts = readMarkdown.makeProductList();
        assertEquals(testProducts.get(0).getName(), "콜라");
        assertEquals(testProducts.get(0).getPrice(), 1000);
        assertEquals(testProducts.get(0).getQuantity(), 10);
        assertEquals(testProducts.size(), 16);
        assertNotNull(testProducts.get(0).getPromotion());
        assertNull(testProducts.get(1).getPromotion());
    }

}
