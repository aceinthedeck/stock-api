package com.rbc.stocks.services;

import com.rbc.stocks.dto.CreateStockDataDto;
import com.rbc.stocks.models.StockData;
import com.rbc.stocks.repositories.StockDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.rbc.stocks.services.Formatter.parseDoubleOrNull;
import static com.rbc.stocks.services.Formatter.parseLongOrNull;
import static com.rbc.stocks.services.Formatter.removeCurrencySymbolFromPrice;
import static com.rbc.stocks.services.Formatter.stringToDate;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService{

    private final StockDataRepository stockDataRepository;

    @Override
    public Mono<StockData> add(StockData stockData) {
        return stockDataRepository.save(stockData)
                .switchIfEmpty(Mono.defer(()-> Mono.error(new RuntimeException("Error in adding stock data"))));
    }

    @Override
    public Flux<StockData> findAllByTicker(String ticker) {
        return stockDataRepository.findAllByTicker(ticker);
    }

    @Override
    public Mono<StockData> create(CreateStockDataDto dto) {
       var newData = StockData.builder()
                .quarter(Integer.parseInt(dto.getQuarter()))
                .ticker(dto.getTicker())
                .date(stringToDate(dto.getDate(), "M/d/uuuu"))
                .open(parseDoubleOrNull(removeCurrencySymbolFromPrice(dto.getOpen())))
                .close(parseDoubleOrNull(removeCurrencySymbolFromPrice(dto.getClose())))
                .high(parseDoubleOrNull(removeCurrencySymbolFromPrice(dto.getHigh())))
                .low(parseDoubleOrNull(removeCurrencySymbolFromPrice(dto.getLow())))
                .volume(parseLongOrNull(dto.getVolume()))
                .percentChangePrice(parseDoubleOrNull(dto.getPercentChangePrice()))
                .percentChangeVolumeOverLastWeek(parseDoubleOrNull(dto.getPercentChangeVolumeOverLastWeek()))
                .previousWeekVolume(parseLongOrNull(dto.getPreviousWeekVolume()))
                .nextWeekOpen(parseDoubleOrNull(removeCurrencySymbolFromPrice(dto.getNextWeekOpen())))
                .nextWeekClose(parseDoubleOrNull(removeCurrencySymbolFromPrice(dto.getNextWeekClose())))
                .percentChangeNextWeekPrice(parseDoubleOrNull(dto.getPercentChangeNextWeekPrice()))
                .daysToNextDividend(Integer.parseInt(dto.getDaysToNextDividend()))
                .percentReturnNextDividend(parseDoubleOrNull(dto.getPercentReturnNextDividend()))
                .build();

       return stockDataRepository.save(newData)
               .switchIfEmpty(Mono.defer(()-> Mono.error(new RuntimeException("Error in adding stock data"))));
    }
}
