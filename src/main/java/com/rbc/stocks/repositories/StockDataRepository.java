package com.rbc.stocks.repositories;

import com.rbc.stocks.models.StockData;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface StockDataRepository extends R2dbcRepository<StockData, Long> {

    Flux<StockData> findAllByTicker(String ticker);
}
