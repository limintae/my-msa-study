package com.msa.example.order.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@ToString
@Table(name = "store")
@AllArgsConstructor
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(columnDefinition = "ENUM('NORMAL', 'HIDDEN')")
    @Enumerated(EnumType.STRING)
    private OTYPE otype;

    public StoreEntity() {}

}
