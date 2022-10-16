package com.rbc.stocks.controllers;

import com.rbc.stocks.dto.CreateStockDataDto;
import com.rbc.stocks.models.StockData;
import com.rbc.stocks.services.FileParserService;
import com.rbc.stocks.services.StockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebFluxTest(StockController.class)
@Import(StockService.class)
public class StockControllerTest {

    @MockBean
    StockService stockService;

    @MockBean
    FileParserService fileParserService;

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void testGetStocksByTicker() {

        // Added only ticker to save time.
        var stockData = StockData.builder()
                .id(1)
                .ticker("ABC")
                .build();

        Mockito.when(stockService.findAllByTicker("ABC"))
                .thenReturn(Flux.just(stockData));

        webTestClient.get()
                .uri("/stocks/ABC")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StockData.class);
    }

    @Test
    public void createStockDataBadRequestTest() {

        var stockDataDTo = CreateStockDataDto.builder()
                .ticker("ABC")
                .build();

        var stockData = StockData.builder()
                .id(1)
                .ticker("ABC")
                .build();

        Mockito.when(stockService.create(stockDataDTo))
                .thenReturn(Mono.just(stockData));

        webTestClient.post()
                .uri("/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(stockDataDTo)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void createStockDataNullBodyTest() {

        Mockito.when(stockService.create(any()))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/stocks")
                .exchange()
                .expectStatus().isBadRequest();
    }

}
