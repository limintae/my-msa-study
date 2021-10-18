package com.msa.example.order.web.rest;

import com.msa.example.order.adaptor.AuthClient;
import com.msa.example.order.adaptor.dto.MemberResponseDto;
import com.msa.example.order.domain.OTYPE;
import com.msa.example.order.domain.StoreEntity;
import com.msa.example.order.domain.StoreOrderEntity;
import com.msa.example.order.repository.StoreOrderRepository;
import com.msa.example.order.repository.StoreRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/order")
public class OrderRestController {

    private final StoreRepository storeRepository;
    private final StoreOrderRepository storeOrderRepository;
    private final AuthClient authClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    /**
     * order api
     * @return boolean
     */
    @PostMapping(value = "/buy")
    public boolean buyItem() {
        storeRepository.save(StoreEntity.builder()
                .name("GEGE")
                .otype(OTYPE.NORMAL).build()
        );
        storeOrderRepository.save(StoreOrderEntity.builder().orderName("GIGI").build());
        log.info("buy item!!");
        return true;
    }

    /**
     * 인증서버에서 토큰 인증정보 리턴
     * @param request
     * @return
     */
    @GetMapping(value = "/my-info")
    public ResponseEntity<MemberResponseDto> getMemberInfo(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");

        // Using Circuit Breaker
//        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
//        ResponseEntity<MemberResponseDto> response = circuitBreaker.run(() -> authClient.getMemberInfo(accessToken),
//                throwable -> new ResponseEntity(HttpStatus.BAD_GATEWAY));

        // Using Feign Exception
        ResponseEntity<MemberResponseDto> response = authClient.getMemberInfo(accessToken);
        return response;
    }

}
