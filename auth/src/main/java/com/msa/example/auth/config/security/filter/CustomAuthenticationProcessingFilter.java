package com.msa.example.auth.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.msa.example.auth.config.security.handler.JwtAuthenticationFailureHandler;
import com.msa.example.auth.config.security.handler.JwtAuthenticationSuccessHandler;
import com.msa.example.auth.web.rest.dto.MemberRequestDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationProcessingFilter(
            ObjectMapper objectMapper,
            AntPathRequestMatcher antPathRequestMatcher,
            AuthenticationManager authenticationManager,
            JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler,
            JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler) {
        super(antPathRequestMatcher);
        this.objectMapper = objectMapper;
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);
        this.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

//        String email = "limintae2@gmail.com";
//        String password = "limintae7897";

        MemberRequestDto memberRequestDto;
        try {
            memberRequestDto = objectMapper.readValue(request.getReader(), MemberRequestDto.class);
        } catch (MismatchedInputException e) {
            return null;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                memberRequestDto.getEmail(),
                memberRequestDto.getPassword()
        );
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

}
