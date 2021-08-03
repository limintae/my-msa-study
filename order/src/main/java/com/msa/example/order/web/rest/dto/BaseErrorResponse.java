package com.msa.example.order.web.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Getter
@RequiredArgsConstructor
@Builder
public class BaseErrorResponse {

    private final int status;
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

}
