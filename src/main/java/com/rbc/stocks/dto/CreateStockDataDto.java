package com.rbc.stocks.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class CreateStockDataDto {

    // only basic validation added to save time
    // in real life these will be custom validations based on the input constraints
    @NotNull
    private String quarter;

    @NotNull
    private String ticker;

    @NotNull
    private String date;

    @NotNull
    private String open;

    @NotNull
    private String high;

    @NotNull
    private String low;

    @NotNull
    private String close;

    @NotNull
    private String volume;

    @NotNull
    private String percentChangePrice;

    @NotNull
    private String percentChangeVolumeOverLastWeek;

    @NotNull
    private String previousWeekVolume;

    @NotNull
    private String nextWeekOpen;

    @NotNull
    private String nextWeekClose;

    @NotNull
    private String percentChangeNextWeekPrice;

    @NotNull
    private String daysToNextDividend;

    @NotNull
    private String percentReturnNextDividend;
}
