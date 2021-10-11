package com.msa.example.auth.service;

import com.msa.example.auth.domain.entity.Authority;
import com.msa.example.auth.domain.entity.Role;
import com.msa.example.auth.domain.enums.AuthorityStatus;
import com.msa.example.auth.domain.enums.RoleStatus;
import com.msa.example.auth.repository.AuthorityRepository;
import com.msa.example.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("RoleService")
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public void createRoleAuthority(RoleStatus roleStatus, AuthorityStatus authorityStatus) {
        log.info("start createRoleAuthority");
        Role role = Optional.ofNullable(roleRepository.findByName(roleStatus)).
                orElseThrow(() -> new RuntimeException("role is not found"));
        Authority authority = authorityRepository.findByName(authorityStatus);

        List<Authority> authorities = Optional.ofNullable(role.getAuthorities()).orElse(new ArrayList<>());
        authorities.add(authority);
        roleRepository.save(role);

        // 역할별 권한 생성
//        RoleAuthorityId roleAuthorityId = RoleAuthorityId.builder()
//                .roleId(role.getId())
//                .authorityId(authority.getId())
//                .build();
//        RoleAuthority roleAuthority = RoleAuthority.builder()
//                .roleAuthorityId(roleAuthorityId)
//                .role(role)
//                .authority(authority)
//                .build();

        // 권한 등록
//        Set<RoleAuthority> roleAuthorities = Optional.ofNullable(role.getRoleAuthorities()).orElse(new HashSet<>());
//        roleAuthorities.add(roleAuthority);
//        role.setRoleAuthorities(roleAuthorities);
//        roleRepository.save(role);
        log.info("success createRoleAuthority");

    }

}
