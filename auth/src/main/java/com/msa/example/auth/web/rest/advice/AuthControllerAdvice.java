package com.msa.example.auth.web.rest.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity runtimeExceptionHandler(final RuntimeException exception) {
        return ResponseEntity.ok(exception.getMessage());
    }

}
