package com.msa.example.auth.config.security.filter;

import com.msa.example.auth.config.security.handler.JwtAuthenticationFailureHandler;
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

    public CustomAuthenticationProcessingFilter(AntPathRequestMatcher antPathRequestMatcher, AuthenticationManager authenticationManager, JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler) {
        // super(defaultFilterProcessesUrl);
        super(antPathRequestMatcher);
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String email = "limintae2@gmail.com";
        String password = "limintae7897";

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

}
