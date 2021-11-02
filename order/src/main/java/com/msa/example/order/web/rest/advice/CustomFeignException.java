package com.msa.example.order.web.rest.advice;

import lombok.Getter;

@Getter
public class CustomFeignException extends RuntimeException {

    private final int status;

    public CustomFeignException(Integer status, String errorMessage) {
        super(errorMessage);
        this.status = status;
    }

}