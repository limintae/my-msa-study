package com.msa.example.auth.config.security.filter;

import com.msa.example.auth.config.security.TokenValidStatus;
import com.msa.example.auth.config.security.exception.JwtExpiredTokenException;
import com.msa.example.auth.config.security.handler.JwtAuthenticationEntryPoint;
import com.msa.example.auth.config.security.provider.TokenProvider;
import com.msa.example.auth.service.AuthService;
import com.msa.example.auth.web.rest.dto.TokenDto;
import com.msa.example.auth.web.rest.dto.TokenRequestDto;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
    public static final String BEARER_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;
    private final AuthService authService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public JwtFilter(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                     TokenProvider tokenProvider,
                     AuthService authService) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.tokenProvider = tokenProvider;
        this.authService = authService;
    }

    // 실제 필터링 로직은 doFilterInternal 에 들어감
    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {

        // 1. Request Header 에서 토큰을 꺼냄
        String accessToken = resolveToken(request, AUTHORIZATION_HEADER);
        if (StringUtils.hasText(accessToken)) {
            setSecurityContextHolder(accessToken);
        }

        // 2. validateToken 으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
//        if (StringUtils.hasText(accessToken)) {
//            switch (tokenProvider.validateToken(accessToken)) {
//                case SUCCESS:
//                    setSecurityContextHolder(accessToken);
//                    // this.jwtAuthenticationEntryPoint.commence(request, response, new JwtExpiredTokenException("dd"));
//                    break;
//                case EXPIRED:
//                    // Refresh
//                    TokenDto tokenDto = authService.reissue(
//                            TokenRequestDto.builder()
//                                .accessToken(accessToken)
//                                .refreshToken(refreshToken)
//                                .build()
//                    );
//                    setSecurityContextHolder(tokenDto.getAccessToken());
//                    break;
//                case ERROR:
//                    this.jwtAuthenticationEntryPoint.commence(
//                            request,
//                            response,
//                            new BadCredentialsException(TokenValidStatus.ERROR.getDescription())
//                    );
//                    break;
//                case UNSUPPORTED:
//                    this.jwtAuthenticationEntryPoint.commence(
//                            request,
//                            response,
//                            new BadCredentialsException(TokenValidStatus.UNSUPPORTED.getDescription())
//                    );
//                    break;
//                case WRONG_SIGNATURE:
//                    this.jwtAuthenticationEntryPoint.commence(
//                            request,
//                            response,
//                            new BadCredentialsException(TokenValidStatus.WRONG_SIGNATURE.getDescription())
//                    );
//                    break;
//                default:
//                    break;
//            }
//        }
        filterChain.doFilter(request, response);
    }

    private void setSecurityContextHolder(String accessToken) {
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    // Request Header 에서 토큰 정보를 꺼내오기
    private String resolveToken(HttpServletRequest request, String header) {
        String bearerToken = request.getHeader(header);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
