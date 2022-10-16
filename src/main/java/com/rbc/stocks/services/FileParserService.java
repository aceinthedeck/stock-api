package com.rbc.stocks.services;

import com.rbc.stocks.models.StockData;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

public interface FileParserService {

    Flux<StockData> parse(Flux<DataBuffer> dataBuffer);

}
