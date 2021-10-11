package com.msa.example.auth.service;

import com.msa.example.auth.web.rest.dto.MemberRequestDto;
import com.msa.example.auth.web.rest.dto.MemberResponseDto;
import com.msa.example.auth.web.rest.dto.TokenDto;
import com.msa.example.auth.web.rest.dto.TokenRequestDto;

public interface AuthService {

    MemberResponseDto signup(MemberRequestDto memberRequestDto);
    TokenDto login(MemberRequestDto memberRequestDto);
    TokenDto reissue(TokenRequestDto tokenRequestDto);

}
