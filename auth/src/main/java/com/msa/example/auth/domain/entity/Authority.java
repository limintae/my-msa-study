package com.msa.example.auth.domain.entity;

import com.msa.example.auth.domain.enums.AuthorityStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "authority")
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthorityStatus name;

    @ManyToMany(mappedBy = "authorities", cascade = {CascadeType.PERSIST})
    private List<Role> roles;

    @Builder
    public Authority(AuthorityStatus name, List<Role> roles) {
        this.name = name;
        this.roles = roles;
    }

}
