package com.msa.example.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Builder
@ToString
@Table(name = "store_order")
@AllArgsConstructor
public class StoreOrderEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID orderId;

    @Column(name = "ORDER_NAME")
    private String orderName;

    public StoreOrderEntity() {

    }

}
