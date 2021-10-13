package com.msa.example.auth.repository;

import com.msa.example.auth.domain.entity.Role;
import com.msa.example.auth.domain.enums.RoleStatus;

public interface RoleCustomRepository {

    Role findByName(RoleStatus roleStatus);

}
