package com.example.demo2;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="service-1")
public class FeignTestClient {

}
