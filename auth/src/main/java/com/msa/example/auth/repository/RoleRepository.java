package com.msa.example.auth.repository;

import com.msa.example.auth.domain.entity.Role;
import com.msa.example.auth.domain.enums.RoleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleStatus roleStatus);

}
