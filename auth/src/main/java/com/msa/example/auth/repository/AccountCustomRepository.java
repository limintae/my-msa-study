package com.msa.example.auth.repository;

import com.msa.example.auth.domain.entity.Account;

public interface AccuontCustomRepository {

    Account findByEmail(String email);

    boolean existsByEmail(String email);

}
