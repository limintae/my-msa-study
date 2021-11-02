package com.msa.example.gateway.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class JwtErrorResponse {

    private int code;
    private String message;
    private final long timestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();

    public JwtErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
