package com.msa.example.order.config.web;

import com.msa.example.order.web.rest.exception.CustomFeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("FeignErrorDecoder error");
        switch (response.status()) {
            case 200:
                break;
            case 404:
                if (methodKey.contains("getMemberInfo")) {
                    return new CustomFeignException(response.status(), "member is not found");
                }
                break;
            case 401:
                return new CustomFeignException(response.status(), "Authorization error");
            default:
                return new CustomFeignException(response.status(), response.reason());
        }
        return null;
    }

}
