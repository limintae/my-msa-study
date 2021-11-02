package com.msa.example.auth.service;

import com.msa.example.auth.domain.entity.Authority;
import com.msa.example.auth.domain.entity.Role;
import com.msa.example.auth.domain.enums.AuthorityStatus;
import com.msa.example.auth.domain.enums.RoleStatus;
import com.msa.example.auth.repository.AuthorityRepository;
import com.msa.example.auth.repository.RoleCustomRepository;
import com.msa.example.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service("RoleService")
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleCustomRepository roleCustomRepository;
    private final AuthorityRepository authorityRepository;

    public RoleServiceImpl(
            RoleRepository roleRepository,
            RoleCustomRepository roleCustomRepository,
            AuthorityRepository authorityRepository) {
        this.roleRepository = roleRepository;
        this.roleCustomRepository = roleCustomRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void createRoleAuthority(RoleStatus roleStatus, AuthorityStatus authorityStatus) {
        log.info("start createRoleAuthority");
        Role role = Optional.ofNullable(roleCustomRepository.findByName(roleStatus)).
                orElseThrow(() -> new RuntimeException("role is not found"));
        Authority authority = authorityRepository.findByName(authorityStatus);

        Set<Authority> authorities = Optional.ofNullable(role.getAuthorities()).orElse(new HashSet<>());
        authorities.add(authority);
        roleRepository.save(role);

        log.info("success createRoleAuthority");
    }

}
