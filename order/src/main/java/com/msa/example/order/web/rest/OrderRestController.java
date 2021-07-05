package com.msa.example.order.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderRestController {

    @GetMapping(value = "/buy")
    public boolean buyItem() {
        log.info("buy item!!");
        return true;
    }

}
