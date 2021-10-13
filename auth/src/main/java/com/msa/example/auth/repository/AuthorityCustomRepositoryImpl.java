package com.msa.example.auth.repository;

import com.msa.example.auth.domain.entity.Authority;
import com.msa.example.auth.domain.enums.AuthorityStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.msa.example.auth.domain.entity.QAuthority.authority;
import static com.msa.example.auth.domain.entity.QRole.role;

@Repository
public class AuthorityCustomRepositoryImpl extends QuerydslRepositorySupport implements AuthorityCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public AuthorityCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Authority.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Authority findByName(AuthorityStatus status) {
        return null;
    }

}
