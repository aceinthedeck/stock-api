package com.rbc.stocks.services;

import com.rbc.stocks.dto.CreateStockDataDto;
import com.rbc.stocks.models.StockData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockService {

    Mono<StockData> add(StockData stockData);

    Flux<StockData> findAllByTicker(String ticker);

    Mono<StockData> create(CreateStockDataDto dto);
}
