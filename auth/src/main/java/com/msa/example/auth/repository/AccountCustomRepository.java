package com.msa.example.auth.repository;

import com.msa.example.auth.domain.entity.Account;

public interface AccountCustomRepository {

    Account findByEmail(String email);

    boolean existsByEmail(String email);

}
