package model;

import static java.lang.Integer.parseInt;
import static store.ErrorType.INCORRECT_PROMOTION_ERROR;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatConversionException;
import java.util.IllegalFormatPrecisionException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ReadMarkdown {

    private List<List<String>> promotionInfoes = new ArrayList<>();
    private static final String promotionPath ="src/main/resources/promotions.md";
    private static final String productsPath ="src/main/resources/products.md";

    public ReadMarkdown() {
        readPromotion();
    }

    private void readPromotion() {
        List<String> lines = useBufferToRead(promotionPath);
        for (String line : lines) {
            this.promotionInfoes.add(parseInput(line));
        }
    }

    private List<String> useBufferToRead(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return lines.subList(1, lines.size());
    }

    private List<String> parseInput(String line) {
        return Stream.of(line.split(",")).map(String::trim).toList();
    }

    public List<Product> makeProductList() {
        List<String> lines = useBufferToRead(productsPath);
        List<Product> products = new ArrayList<>();
        for (String line : lines) {
            products.add(makeProduct(line));
        }
        return products;
    }

    private Product makeProduct(String line) {
            List<String> parseInfo = parseInput(line);
            if (!parseInfo.get(3).equals("null")) {
                try {
                    return new Product(parseInfo.get(0), parseInt(parseInfo.get(1)), parseInt(parseInfo.get(2)),
                            matchPromotion(parseInfo.get(3)));
                } catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
            }
            return new Product(parseInfo.get(0), parseInt(parseInfo.get(1)), parseInt(parseInfo.get(2)));
    }

    private List<String> matchPromotion(String promotion) throws IllegalArgumentException{
        for (List<String> promotionInfo : promotionInfoes) {
            if (Objects.equals(promotionInfo.get(0), promotion)){
                return promotionInfo;
            }
        }
        throw new IllegalArgumentException(INCORRECT_PROMOTION_ERROR.getErrorMessage());
    }

}
