package com.msa.example.order.config.web;

import com.msa.example.order.web.rest.advice.CustomFeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final Environment env;

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("FeignErrorDecoder error");
        switch (response.status()) {
            case 200:
                break;
            case 404:
                // 특정 Method에 대한 에러처리
                if (methodKey.contains("getMemberInfo")) {
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "member is not found");
                }
                break;
            case 401:
                return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization error");
//                return new CustomFeignException(response.status(), "Authorization error");
            default:
                return new CustomFeignException(response.status(), response.reason());
        }
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
