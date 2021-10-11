package com.msa.example.auth.service;

import com.msa.example.auth.domain.enums.AuthorityStatus;
import com.msa.example.auth.domain.enums.RoleStatus;

public interface RoleService {

    void createRoleAuthority(RoleStatus roleStatus, AuthorityStatus authorityStatus);

}
