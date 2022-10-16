package com.rbc.stocks.services;

import com.rbc.stocks.models.StockData;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVFileParserServiceTest {

    CSVFileParserService csvFileParserService = new CSVFileParserService();

    @Test
    void parseCSVTest() throws IOException {

        byte[] content = Files.readAllBytes(Paths.get("src/test/resources/dow_jones_index.data"));
        DefaultDataBuffer dataBuffer = DefaultDataBufferFactory.sharedInstance.wrap(ByteBuffer.wrap(content));

        Flux<DataBuffer> fileData = Flux.just(dataBuffer);

        Flux<StockData> stockDataFlux = csvFileParserService.parse(fileData);

        // in reality, we should test many more fields not just the ticker
        StepVerifier.create(stockDataFlux)
                .expectNextMatches(stockData -> stockData.getTicker().equals("AA")) // test the first element to be AA
                .expectNextCount(11l) // check if 11 elements remaining in the stream
                .verifyComplete();
    }

    @Test
    void parseCSVTestEmptyFlux() throws IOException {

        byte[] content =  "foo".getBytes(StandardCharsets.UTF_8);
        DefaultDataBuffer dataBuffer = DefaultDataBufferFactory.sharedInstance.wrap(ByteBuffer.wrap(content));

        Flux<DataBuffer> fileData = Flux.just(dataBuffer);

        Flux<StockData> stockDataFlux = csvFileParserService.parse(fileData);

        StepVerifier.create(stockDataFlux)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void parseCSVInvalidFileTest() throws IOException {

        byte[] content = Files.readAllBytes(Paths.get("src/test/resources/invalid_index.data"));
        DefaultDataBuffer dataBuffer = DefaultDataBufferFactory.sharedInstance.wrap(ByteBuffer.wrap(content));

        Flux<DataBuffer> fileData = Flux.just(dataBuffer);

        Flux<StockData> stockDataFlux = csvFileParserService.parse(fileData);

        // file with invalid columns
        StepVerifier.create(stockDataFlux)
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException)
                .verify();
    }

}
