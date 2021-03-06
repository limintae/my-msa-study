package com.msa.example.auth.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.example.auth.config.security.filter.CustomAuthenticationProcessingFilter;
import com.msa.example.auth.config.security.filter.JwtFilter;
import com.msa.example.auth.config.security.handler.JwtAccessDeniedHandler;
import com.msa.example.auth.config.security.handler.JwtAuthenticationEntryPoint;
import com.msa.example.auth.config.security.handler.JwtAuthenticationFailureHandler;
import com.msa.example.auth.config.security.handler.JwtAuthenticationSuccessHandler;
import com.msa.example.auth.config.security.provider.CustomAuthenticationProvider;
import com.msa.example.auth.config.security.provider.TokenProvider;
import com.msa.example.auth.domain.CustomUserDetailsService;
import com.msa.example.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenProvider tokenProvider;
    private final AuthService authService;
    private final PasswordEncoderConfig passwordEncoderConfig;

    @Override
    public void configure(WebSecurity web) {
//        web.ignoring();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtFilter customFilter = new JwtFilter(jwtAuthenticationEntryPoint, tokenProvider, authService);

        http.csrf().disable()

                // exception handling ??? ??? ????????? ?????? ???????????? ??????
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // h2-console ??? ?????? ????????? ??????
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // ??????????????? ??????????????? ????????? ??????
                // ???????????? ????????? ???????????? ?????? ????????? ?????? ????????? Stateless ??? ??????
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // ?????????, ???????????? API ??? ????????? ?????? ???????????? ????????? ???????????? ????????? permitAll ??????
                .and()
                .authorizeRequests()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/h2/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/api/v1/user/**").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()   // ????????? API ??? ?????? ?????? ??????

                // JwtFilter ??? addFilterBefore ??? ???????????? JwtSecurityConfig ???????????? ??????
                .and()
                .addFilterBefore(customAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter() throws Exception {
        CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter(
                this.objectMapper,
                new AntPathRequestMatcher("/auth/login", "POST"),
                this.authenticationManager(),
                jwtAuthenticationFailureHandler,
                jwtAuthenticationSuccessHandler);
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new CustomAuthenticationProvider(this.passwordEncoderConfig.passwordEncoder(), this.customUserDetailsService));
    }

    @Bean
    JwtAuthenticationFailureHandler authenticationFailureHandler(){
        return new JwtAuthenticationFailureHandler();
    }

}
