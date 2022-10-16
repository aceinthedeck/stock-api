package com.rbc.stocks.services;

import com.rbc.stocks.models.StockData;
import com.rbc.stocks.repositories.StockDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;

public class StockServiceTest {

    private StockDataRepository stockDataRepository = Mockito.mock(StockDataRepository.class);

    private StockServiceImpl stockService;

    @BeforeEach
    void setup() {
        stockService = new StockServiceImpl(stockDataRepository);
    }

    @Test
    public void findAllByTickerTest() {

        StockData data = StockData.builder()
                .id(1)
                .ticker("ABC")
                .build();

        StockData data2 = StockData.builder()
                        .id(2)
                        .ticker("ABC")
                        .build();

        Mockito.when(stockDataRepository.findAllByTicker("ABC")).thenReturn(Flux.just(data, data2));

        StepVerifier.create(stockService.findAllByTicker("ABC"))
                .expectNextMatches(stock1 -> stock1.getTicker().equals("ABC"))
                .expectNextMatches(stock2 -> stock2.getTicker().equals("ABC"))
                .verifyComplete();
    }

    @Test
    public void testFindAllNone(){
        Mockito.when(stockDataRepository.findAllByTicker(anyString())).thenReturn(Flux.empty());

        StepVerifier.create(stockService.findAllByTicker("ABC"))
                .expectNextCount(0)
                .verifyComplete();
    }

}
