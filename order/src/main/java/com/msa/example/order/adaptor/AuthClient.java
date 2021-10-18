package com.msa.example.order.adaptor;

import com.msa.example.order.adaptor.dto.MemberResponseDto;
import com.msa.example.order.config.security.TokenDto;
import com.msa.example.order.config.security.TokenRequestDto;
import com.msa.example.order.config.web.AuthFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        value = "auth-service",
//        url = "http://localhost:8083",
        configuration = AuthFeignConfig.class)
public interface AuthClient {

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/api/v1/user/info",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MemberResponseDto> getMemberInfo(@RequestHeader("Authorization") String authorizationHeader);

    @RequestMapping(method = RequestMethod.POST, path = "/auth/reissue")
    ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto);

}
