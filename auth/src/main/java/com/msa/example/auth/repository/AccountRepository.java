package com.msa.example.auth.repository;

import com.msa.example.auth.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

//    @Query("select a from Account a join fetch a.roles r join fetch r.authorities")
//    Account findByEmail(String email);

    boolean existsByEmail(String email);

}
