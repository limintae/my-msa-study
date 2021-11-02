package com.msa.example.gateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.example.gateway.dto.JwtErrorResponse;
import com.msa.example.gateway.enums.TokenValidStatus;
import com.msa.example.gateway.service.TokenValidator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomAuthFilter extends AbstractGatewayFilterFactory<CustomAuthFilter.Config> {

    private final ObjectMapper objectMapper;
    private final TokenValidator tokenValidator;

    public CustomAuthFilter(TokenValidator tokenValidator) {
        super(Config.class);
        this.objectMapper = new ObjectMapper();
        this.tokenValidator = tokenValidator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            String accessToken = tokenValidator.resolveToken(exchange.getRequest(), TokenValidator.AUTHORIZATION_HEADER);

            if (StringUtils.hasText(accessToken)) {
                TokenValidStatus tokenValidStatus = tokenValidator.validateToken(accessToken);

                if (tokenValidStatus != TokenValidStatus.SUCCESS) {
                    return handleUnAuthorized(exchange, tokenValidStatus);
                }
            }
            return chain.filter(exchange);
        }));
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

    private Mono<Void> handleUnAuthorized(ServerWebExchange exchange, TokenValidStatus tokenValidStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        String error = null;
        try {
            error = this.objectMapper.writeValueAsString(JwtErrorResponse.builder()
                    .code(tokenValidStatus.getCode())
                    .message("UNAUTHORIZED")
                    .build());
        } catch (JsonProcessingException e) {
            return exchange.getResponse().setComplete();
        }

        byte[] bytes = error.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

}
