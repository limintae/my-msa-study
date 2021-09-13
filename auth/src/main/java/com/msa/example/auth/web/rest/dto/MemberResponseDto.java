package com.msa.example.auth.web.rest.dto;

import com.msa.example.auth.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

    private String email;

    public static MemberResponseDto of(Account account) {
        return new MemberResponseDto(account.getEmail());
    }

}
