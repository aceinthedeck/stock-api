package com.rbc.stocks.controllers;

import com.rbc.stocks.dto.CreateStockDataDto;
import com.rbc.stocks.models.StockData;
import com.rbc.stocks.services.FileParserService;
import com.rbc.stocks.services.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
@Slf4j
public class StockController {

    private final StockService stockService;
    private final FileParserService fileParserService;

    @PostMapping("upload")
    public Flux<Void> uploadStockData(@NotNull @RequestPart("file") Mono<FilePart> file) {

        return file.flatMapMany(f -> fileParserService.parse(f.content()))
                .flatMap(stockData -> stockService.add(stockData))
                .flatMap(added -> Flux.empty());
    }

    @GetMapping("{ticker}")
    public Flux<StockData> getStockDataByTicker(@PathVariable String ticker) {
        return stockService.findAllByTicker(ticker);
    }

    @PostMapping
    public Mono<StockData> create(@Valid @RequestBody CreateStockDataDto dto){
        return stockService.create(dto);
    }

}
