package com.msa.example.order.config.security.filter;

import com.msa.example.order.adaptor.AuthClient;
import com.msa.example.order.config.security.TokenDto;
import com.msa.example.order.config.security.TokenRequestDto;
import com.msa.example.order.config.security.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
    public static final String BEARER_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;
    private final AuthClient authClient;

    // 실제 필터링 로직은 doFilterInternal 에 들어감
    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {

        // 1. Request Header 에서 토큰을 꺼냄
        String accessToken = resolveToken(request, AUTHORIZATION_HEADER);
        String refreshToken = resolveToken(request, REFRESH_TOKEN_HEADER);

        // 2. validateToken 으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
        if (StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
            Authentication authentication = tokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            ResponseEntity<TokenDto> reissueResponse = authClient.reissue(TokenRequestDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build());

            if (reissueResponse.getStatusCode() == HttpStatus.OK) {
                response.setHeader(AUTHORIZATION_HEADER, reissueResponse.getBody().getAccessToken());
                Authentication authentication = tokenProvider.getAuthentication(reissueResponse.getBody().getAccessToken());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
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
