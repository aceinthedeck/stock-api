package com.rbc.stocks.services;

import com.rbc.stocks.models.StockData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.rbc.stocks.services.Formatter.parseDoubleOrNull;
import static com.rbc.stocks.services.Formatter.parseIntOrNull;
import static com.rbc.stocks.services.Formatter.parseLongOrNull;
import static com.rbc.stocks.services.Formatter.removeCurrencySymbolFromPrice;
import static com.rbc.stocks.services.Formatter.stringToDate;

@Service
public class CSVFileParserService implements FileParserService{


    public Flux<StockData> parse(Flux<DataBuffer> dataBuffer) {

        return dataBuffer.flatMap(buffer -> {
            final InputStreamReader streamReader = new InputStreamReader(buffer.asInputStream());
            final BufferedReader bufferedReader = new BufferedReader(streamReader);
                    try {
                        final CSVParser csvParser = new CSVParser(bufferedReader,
                                                                  CSVFormat.DEFAULT
                                                                          .withFirstRecordAsHeader()
                                                                          .withIgnoreHeaderCase()
                                                                          .withTrim());
                        return Flux.fromIterable(csvParser.getRecords());
                    } catch(IOException e) {
                       return Flux.error(e);
                    }
        })
        .flatMap(row ->
             Flux.just(StockData.builder()
                     .quarter(parseIntOrNull(row.get("quarter")))
                     .ticker(row.get("stock"))
                     .date(stringToDate(row.get("date"), "M/d/uuuu"))
                     .open(parseDoubleOrNull(removeCurrencySymbolFromPrice(row.get("open"))))
                     .close(parseDoubleOrNull(removeCurrencySymbolFromPrice(row.get("close"))))
                     .high(parseDoubleOrNull(removeCurrencySymbolFromPrice(row.get("high"))))
                     .low(parseDoubleOrNull(removeCurrencySymbolFromPrice(row.get("low"))))
                     .volume(parseLongOrNull(row.get("volume")))
                     .percentChangePrice(parseDoubleOrNull(row.get("percent_change_price")))
                     .percentChangeVolumeOverLastWeek(parseDoubleOrNull(row.get("percent_change_volume_over_last_wk")))
                     .previousWeekVolume(parseLongOrNull(row.get("previous_weeks_volume")))
                     .nextWeekOpen(parseDoubleOrNull(removeCurrencySymbolFromPrice(row.get("next_weeks_open"))))
                     .nextWeekClose(parseDoubleOrNull(removeCurrencySymbolFromPrice(row.get("next_weeks_close"))))
                     .percentChangeNextWeekPrice(parseDoubleOrNull(row.get("percent_change_next_weeks_price")))
                     .daysToNextDividend(parseIntOrNull(row.get("days_to_next_dividend")))
                     .percentReturnNextDividend(parseDoubleOrNull(row.get("percent_return_next_dividend")))
                     .build())
        );
    }
}
