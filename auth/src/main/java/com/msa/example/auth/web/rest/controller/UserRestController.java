package com.msa.example.auth.web.rest.controller;

import com.msa.example.auth.domain.AccountInfo;
import com.msa.example.auth.service.MemberService;
import com.msa.example.auth.web.rest.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final MemberService memberService;

    @GetMapping(path = "/token-info")
    public ResponseEntity<AccountInfo> getTokenInfo(
            @AuthenticationPrincipal AccountInfo accountInfo) {
        log.info("member email is : " + accountInfo.getEmail());
        return ResponseEntity.ok(accountInfo);
    }

    @GetMapping(path = "/info")
    public ResponseEntity<MemberResponseDto> getMemberInfo(
            @AuthenticationPrincipal AccountInfo accountInfo) {
        log.info("member email is : " + accountInfo.getEmail());
        return ResponseEntity.ok(MemberResponseDto.builder()
                .email(accountInfo.getEmail())
                .build());
    }

}
