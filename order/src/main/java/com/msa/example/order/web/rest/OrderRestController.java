package com.msa.example.order.web.rest;

import com.msa.example.order.domain.OTYPE;
import com.msa.example.order.domain.StoreEntity;
import com.msa.example.order.domain.StoreOrderEntity;
import com.msa.example.order.repository.StoreOrderRepository;
import com.msa.example.order.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final StoreRepository storeRepository;
    private final StoreOrderRepository storeOrderRepository;

    /**
     * jpa test
     * @return
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

}
