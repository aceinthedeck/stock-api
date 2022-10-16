package com.rbc.stocks.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ErrorResponse {

    List<Error> errors;
}
