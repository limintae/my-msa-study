package com.msa.example.auth.repository;

import com.msa.example.auth.domain.entity.Account;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.msa.example.auth.domain.entity.QAccount.account;
import static com.msa.example.auth.domain.entity.QRole.role;
import static com.msa.example.auth.domain.entity.QAuthority.authority;

@Repository
public class AccountCustomRepositoryImpl extends QuerydslRepositorySupport implements AccountCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public AccountCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Account.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Account findByEmail(String email) {
        return jpaQueryFactory.selectFrom(account)
                .leftJoin(account.roles, role)
                .fetchJoin()
                .leftJoin(role.authorities, authority)
                .fetchJoin()
                .where(account.email.eq(email))
                .fetchOne();
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

}
