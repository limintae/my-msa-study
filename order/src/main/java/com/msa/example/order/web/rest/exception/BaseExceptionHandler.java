package com.msa.example.order.web.rest.exception;

import com.msa.example.order.web.rest.dto.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestControllerAdvice(basePackages = {"com.msa.example.order"})
public class BaseExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseErrorResponse> exception(RuntimeException ex) {
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

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<BaseErrorResponse> responseStatusException(ResponseStatusException ex) {
        log.error(ex.getReason());

        return new ResponseEntity<>(
                BaseErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message(ex.getReason())
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomFeignException.class)
    public ResponseEntity<BaseErrorResponse> customFeignException(CustomFeignException ex) {
        log.error(ex.getMessage());

        switch (ex.getStatus()) {
            case 401:
                return new ResponseEntity<>(
                        BaseErrorResponse.builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message(ex.getMessage())
                            .build(),
                        HttpStatus.UNAUTHORIZED);
            default:
                return new ResponseEntity<>(
                        BaseErrorResponse.builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(ex.getMessage())
                                .build(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
