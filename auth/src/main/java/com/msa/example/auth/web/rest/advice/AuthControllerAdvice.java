package com.msa.example.auth.web.rest.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity handle(final UsernameNotFoundException exception) {
        return ResponseEntity.ok(exception.getMessage());
    }

}
