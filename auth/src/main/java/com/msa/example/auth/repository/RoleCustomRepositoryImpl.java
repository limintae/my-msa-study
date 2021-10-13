package com.msa.example.auth.repository;

import com.msa.example.auth.domain.entity.Authority;
import com.msa.example.auth.domain.entity.Role;
import com.msa.example.auth.domain.enums.RoleStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.msa.example.auth.domain.entity.QRole.role;
import static com.msa.example.auth.domain.entity.QAuthority.authority;

@Repository
public class RoleCustomRepositoryImpl extends QuerydslRepositorySupport implements RoleCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public RoleCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Role.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Role findByName(RoleStatus roleStatus) {
        return jpaQueryFactory.selectFrom(role)
                .leftJoin(role.authorities, authority)
                .fetchJoin()
                .where(role.name.eq(roleStatus))
                .fetchOne();
    }

}
