package com.msa.example.order.web.rest.exception;

import com.msa.example.order.web.rest.dto.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {"com.msa.example.order.web"})
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseErrorResponse> exception(Exception ex) {
        log.error(ex.getMessage());
        BaseErrorResponse baseErrorResponse = BaseErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(baseErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseErrorResponse> indexOutOfBoundsException(IndexOutOfBoundsException ex) {
        log.error(ex.getMessage());
        BaseErrorResponse baseErrorResponse = BaseErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(baseErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
