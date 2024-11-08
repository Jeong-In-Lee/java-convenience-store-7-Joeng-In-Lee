package model;

import static java.lang.Integer.parseInt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Promotion {
    private String name;
    private int buy;
    private int get;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Promotion(List<String> promotionInfo) { //String name, int buy, int get, String startDate, String endDate
        this.name = promotionInfo.get(0);
        this.buy = parseInt(promotionInfo.get(1));
        this.get =parseInt(promotionInfo.get(2));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(promotionInfo.get(3), formatter).atStartOfDay();
        this.endDate = LocalDate.parse(promotionInfo.get(4), formatter).atStartOfDay();
    }


    public Boolean checkDate() {
        LocalDateTime todayDate = camp.nextstep.edu.missionutils.DateTimes.now();
        return startDate.isBefore(todayDate) && endDate.isAfter(todayDate);
    }

}
