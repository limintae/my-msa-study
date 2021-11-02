package com.msa.example.gateway.service;

import com.msa.example.gateway.enums.TokenValidStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;

@Component
public class TokenValidator {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String JWT_SECRET = "c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwK";
    private final Key key;

    public TokenValidator() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String resolveToken(ServerHttpRequest request, String header) {
        String bearerToken = "";
        try {
            bearerToken = request.getHeaders().get(header).get(0);
        } catch (Exception e) {
            return null;
        }

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public TokenValidStatus validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
            return TokenValidStatus.SUCCESS;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            return TokenValidStatus.WRONG_SIGNATURE;
        } catch (ExpiredJwtException e) {
            return TokenValidStatus.EXPIRED;
        } catch (UnsupportedJwtException e) {
            return TokenValidStatus.UNSUPPORTED;
        } catch (IllegalArgumentException e) {
            return TokenValidStatus.ERROR;
        }
    }

}
