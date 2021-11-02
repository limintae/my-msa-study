package com.msa.example.gateway.enums;

public enum TokenValidStatus {
    SUCCESS(200, "정상 토큰"),
    ERROR(1001, "잘못된 토큰"),
    EXPIRED(1002, "만료된 토큰"),
    WRONG_SIGNATURE(1003, "잘못된 서명"),
    UNSUPPORTED(1004, "지원되지 않는 토큰");

    private final int code;
    private final String description;

    TokenValidStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
