package com.msa.example.auth.web.rest.dto;

import com.msa.example.auth.domain.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class MemberResponseDto implements Serializable {

    private static final long serialVersionUID = -7654699009594556450L;
    private String email;

    public static MemberResponseDto of(Account account) {
        return new MemberResponseDto(account.getEmail());
    }

    @Builder
    public MemberResponseDto(String email) {
        this.email = email;
    }

}
