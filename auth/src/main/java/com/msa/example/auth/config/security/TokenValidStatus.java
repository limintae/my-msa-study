package com.msa.example.auth.config.security;

public enum TokenValidStatus {
    SUCCESS("정상 토큰"),
    ERROR("잘못된 토큰"),
    EXPIRED("만료된 토큰"),
    WRONG_SIGNATURE("잘못된 서명"),
    UNSUPPORTED("지원되지 않는 토큰");

    private final String description;

    TokenValidStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
