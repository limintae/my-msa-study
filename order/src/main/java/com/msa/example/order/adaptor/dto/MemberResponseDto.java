package com.msa.example.order.adaptor.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class MemberResponseDto implements Serializable {

    private static final long serialVersionUID = 8984426802525572705L;
    private String email;

    @Builder
    public MemberResponseDto(String email) {
        this.email = email;
    }

}
