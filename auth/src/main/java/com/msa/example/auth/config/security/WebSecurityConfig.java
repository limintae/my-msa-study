package com.msa.example.auth.config.security;

import com.msa.example.auth.config.security.filter.CustomAuthenticationProcessingFilter;
import com.msa.example.auth.config.security.handler.JwtAccessDeniedHandler;
import com.msa.example.auth.config.security.handler.JwtAuthenticationEntryPoint;
import com.msa.example.auth.config.security.handler.JwtAuthenticationFailureHandler;
import com.msa.example.auth.config.security.provider.CustomAuthenticationProvider;
import com.msa.example.auth.config.security.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()

                // exception handling 할 때 우리가 만든 클래스를 추가
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // h2-console 을 위한 설정을 추가
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 시큐리티는 기본적으로 세션을 사용
                // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/api/v1/user/**").hasAnyAuthority("ROLE_USER")
                .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요

                // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
                .and()
                .addFilterBefore(customAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
                //.apply(new JwtSecurityConfig(tokenProvider, jwtAuthenticationFailureHandler, this.authenticationManager()));
    }

    private CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter() throws Exception {
        CustomAuthenticationProcessingFilter filter
                = new CustomAuthenticationProcessingFilter(new AntPathRequestMatcher("/auth/login", "POST"), this.authenticationManager(), jwtAuthenticationFailureHandler);
        // filter.setAuthenticationManager(this.authenticationManager());
        return filter;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.customAuthenticationProvider);
    }

//    private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
//            new AntPathRequestMatcher("/v1/**"),new AntPathRequestMatcher("/admin/**")
//    );

//    @Bean
//    AuthenticationFilter authenticationFilter() throws Exception {
//        final AuthenticationFilter filter = new AuthenticationFilter(PROTECTED_URLS);
//        filter.setAuthenticationManager(authenticationManager());
//        filter.setAuthenticationSuccessHandler(successHandler());
//        filter.setAuthenticationFailureHandler(authenticationFailureHandler());
//        return filter;
//    }

    @Bean
    JwtAuthenticationFailureHandler authenticationFailureHandler(){
        return new JwtAuthenticationFailureHandler();
    }

}
