package com.msa.example.auth.repository;

import com.msa.example.auth.domain.entity.Authority;
import com.msa.example.auth.domain.enums.AuthorityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByName(AuthorityStatus status);

}
