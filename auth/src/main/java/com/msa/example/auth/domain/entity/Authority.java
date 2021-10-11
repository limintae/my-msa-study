package com.msa.example.auth.domain.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@NoArgsConstructor
@Table(name = "account_authority")
@Entity
public class AccountAuthority {

    @Id
    @Column(name = "authority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Collection<AccountRole> roles;

}
