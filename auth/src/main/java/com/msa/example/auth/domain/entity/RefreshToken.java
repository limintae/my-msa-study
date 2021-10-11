package com.msa.example.auth.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class RefreshToken {

    @Id
    @Column(name = "token_key")
    private String tokenKey;

    @Column(name = "token_value")
    private String tokenValue;

    public RefreshToken updateValue(String tokenValue) {
        this.tokenValue = tokenValue;
        return this;
    }

    @Builder
    public RefreshToken(String tokenKey, String tokenValue) {
        this.tokenKey = tokenKey;
        this.tokenValue = tokenValue;
    }

}
