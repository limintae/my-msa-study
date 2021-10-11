package com.msa.example.auth.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "account")
@Entity
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", columnDefinition = "VARCHAR(200)")
    private String email;

    @Column(name = "name", columnDefinition = "VARCHAR(20)")
    private String name;

    @Column(name = "password", columnDefinition = "VARCHAR(200)")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(
                    name = "account_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"
            )
    )
    private List<Role> roles;

    @Builder
    public Account(String email, String name, String password, List<Role> roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

}