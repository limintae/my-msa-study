//package com.msa.example.auth.config.security;
//
//import com.msa.example.auth.config.security.filter.CustomAuthenticationProcessingFilter;
//import com.msa.example.auth.config.security.filter.JwtFilter;
//import com.msa.example.auth.config.security.handler.JwtAuthenticationFailureHandler;
//import com.msa.example.auth.config.security.provider.TokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationFilter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@RequiredArgsConstructor
//public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//    private final TokenProvider tokenProvider;
//    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
//    private final AuthenticationManager authenticationManager;
//
//    // TokenProvider 를 주입받아서 JwtFilter 를 통해 Security 로직에 필터를 등록
//    @Override
//    public void configure(HttpSecurity http) {
////        JwtFilter customFilter = new JwtFilter(tokenProvider);
////        CustomAuthenticationProcessingFilter authenticationProcessingFilter
////                = new CustomAuthenticationProcessingFilter(authenticationManager, jwtAuthenticationFailureHandler);
////        http.addFilterBefore(authenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);
////        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//}
