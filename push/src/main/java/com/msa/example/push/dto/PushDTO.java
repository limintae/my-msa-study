package com.msa.example.push.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class PushDTO {

    private String pushId;
    private String name;

    public static PushDTO create(String name) {
        return PushDTO.builder()
                .pushId(UUID.randomUUID().toString())
                .name(name)
                .build();
    }

}
