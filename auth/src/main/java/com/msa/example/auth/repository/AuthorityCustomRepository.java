package com.msa.example.auth.repository;

import com.msa.example.auth.domain.entity.Authority;
import com.msa.example.auth.domain.enums.AuthorityStatus;

public interface AuthorityCustomRepository {

    Authority findByName(AuthorityStatus status);

}
