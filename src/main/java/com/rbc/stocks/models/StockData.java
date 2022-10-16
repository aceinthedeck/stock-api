package com.rbc.stocks.models;

/*
quarter,stock,date,open,high,low,close,volume,percent_change_price,
percent_change_volume_over_last_wk,previous_weeks_volume,
next_weeks_open,next_weeks_close,percent_change_next_weeks_price,
days_to_next_dividend,percent_return_next_dividend
 */

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Table
public class StockData {

    @Id
    private long id;
    private String ticker; // In real world this should be stored in another table
    private Integer quarter;
    private LocalDate date;

    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Long volume;
    private Double percentChangePrice;

    private Long previousWeekVolume;
    private Double percentChangeVolumeOverLastWeek;

    private Double nextWeekOpen;
    private Double nextWeekClose;
    private Double percentChangeNextWeekPrice;

    private Integer daysToNextDividend;
    private Double percentReturnNextDividend;

}
