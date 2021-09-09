package com.msa.example.auth.web.rest.controller;

import com.msa.example.auth.domain.Member;
import com.msa.example.auth.service.MemberService;
import com.msa.example.auth.web.rest.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final MemberService memberService;

    @GetMapping(path = "/{email}")
    public ResponseEntity<MemberResponseDto> getMemberInfo(@PathVariable("email") String email) {

        return ResponseEntity.ok(memberService.getMemberInfo(email));
    }

}
