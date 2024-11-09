package model;

import static java.lang.Integer.parseInt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Promotion {
    private String name;
    private int buy;
    private int get;
    private int set;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Promotion(List<String> promotionInfo) { //String name, int buy, int get, String startDate, String endDate
        this.name = promotionInfo.get(0);
        this.buy = parseInt(promotionInfo.get(1));
        this.get = parseInt(promotionInfo.get(2));
        this.set = buy + get;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(promotionInfo.get(3), formatter).atStartOfDay();
        this.endDate = LocalDate.parse(promotionInfo.get(4), formatter).atStartOfDay();
    }

    public Boolean checkDate() {
        LocalDateTime todayDate = camp.nextstep.edu.missionutils.DateTimes.now();
        return startDate.isBefore(todayDate) && endDate.isAfter(todayDate);
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public int getSet() {
        return set;
    }

    // q 는 주문>프로모션 일 경우 프로모션의 재고 || 주문<프로모션 일 경우 주문 재고
    public List<Integer> applyPromotion(int quantity) {
        List<Integer> info = new ArrayList<>(List.of(0, 0, 0));
        info.set(1, quantity % this.set);
        info.set(0, quantity / this.set);
        return info;
    }
}
