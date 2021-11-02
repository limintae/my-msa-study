package com.msa.example.auth.repository;

import com.msa.example.auth.domain.entity.Account;
import com.msa.example.auth.domain.entity.Authority;
import com.msa.example.auth.domain.entity.Role;
import com.msa.example.auth.domain.enums.AuthorityStatus;
import com.msa.example.auth.domain.enums.RoleStatus;
import com.msa.example.auth.service.AuthService;
import com.msa.example.auth.service.RoleService;
import com.msa.example.auth.web.rest.dto.MemberRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.assertj.core.api.Assertions.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

//@ActiveProfiles("test")
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountRepositoryTest {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthService authService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 테스트 Authority 생성
     * @param authorityRepository authorityRepository
     */
    public static void createAuthority(AuthorityRepository authorityRepository) {
        AuthorityStatus[] AuthorityStatusList = AuthorityStatus.values();
        for (AuthorityStatus authorityStatus : AuthorityStatusList) {
            Authority authority = Authority.builder()
                    .name(authorityStatus)
                    .build();
            authorityRepository.save(authority);
        }
        long savedAuthorityCount = authorityRepository.count();
        assertThat(savedAuthorityCount).isEqualTo(AuthorityStatusList.length);
    }

    /**
     * 테스트 Role 생성
     * @param roleRepository roleRepository
     */
    public static void createRoles(RoleRepository roleRepository) {
        RoleStatus[] roleStatusesList = RoleStatus.values();
        for (RoleStatus roleStatus : roleStatusesList) {
            Role role = Role.builder()
                    .name(roleStatus)
                    .build();
            roleRepository.save(role);
        }
        long savedRoleCount = roleRepository.count();
        assertThat(savedRoleCount).isEqualTo(roleStatusesList.length);
    }

    @BeforeAll
    public void setup() {
        log.info("run before all");
        createAuthority(authorityRepository);
        createRoles(roleRepository);
        roleService.createRoleAuthority(RoleStatus.ROLE_ADMIN, AuthorityStatus.ACCOUNT_WRITE);
        roleService.createRoleAuthority(RoleStatus.ROLE_ADMIN, AuthorityStatus.ACCOUNT_READ);

        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .email("user02@gmail.com")
                .name("임인태")
                .password("12345")
                .role(RoleStatus.ROLE_ADMIN)
                .build();
        authService.signup(memberRequestDto);
    }

    @Test
    @DisplayName("계정생성")
    void createAccount() throws Exception {
//        Account account = Account.builder()
//                .email("user02@gmail.com")
//                .name("임인태")
//                .password("12345")
//                .build();
//
//        accountRepository.save(account);
//
//        long count = accountRepository.count();
//
//        Account savedAccount = accountRepository.findByEmail("user02@gmail.com").get();

//        Role newRole = Role.builder().name(Roles.ROLE_USER).build();
//        Set<Role> roles = new HashSet<>(Arrays.asList(newRole));
//        savedAccount.setRoles(roles);
//
//        accountRepository.save(savedAccount);
//
//        Account updatedAccount = accountRepository.findByEmail("user02@gmail.com").get();
//        Set<Role> updatedRoles = updatedAccount.getRoles();
//
//        System.out.print("end");
    }

}