package com.msa.example.auth.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Getter
@NoArgsConstructor
@Table(name = "account_role", uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "role", "authority"}))
@Entity
public class AccountRole {
    @Id
    @Column(name = "role_id", columnDefinition = "int(3)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority_id", columnDefinition = "int(3)")
    private Long authorityId;

    @ManyToMany
    @JoinTable(
            name = "account_authority",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "authority_id", referencedColumnName = "authority_id"
            )
    )
    private Collection<Authority> authorities;
}
