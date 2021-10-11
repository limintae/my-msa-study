package com.msa.example.auth.domain;

import com.msa.example.auth.domain.entity.Account;
import com.msa.example.auth.domain.entity.Role;
import com.msa.example.auth.repository.AccountRepository;
import com.msa.example.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByEmail(email);
        log.info(email);
        return account
                .map(this::createUserDetails)
                .orElseThrow(() ->
                        new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다.")
                );
    }

    // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Account account) {
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(account.getRoles().toString());

        Collection<SimpleGrantedAuthority> test = Stream.concat(
                getRoles(account.getRoles()).stream(),
                getAuthorities(account.getRoles()).stream()
        ).collect(Collectors.toList());

        AccountInfo accountInfo = AccountInfo.builder()
                .id(account.getId())
                .name(account.getName())
                .password(account.getPassword())
                .email(account.getEmail())
                // .authorities(Collections.singleton(grantedAuthority))
                .authorities(test)
                .build();
        return accountInfo;
    }

    private Collection<SimpleGrantedAuthority> getRoles(List<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().toString())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Collection<SimpleGrantedAuthority> getAuthorities(List<Role> roles) {
        return roles.stream()
                .flatMap(role -> role.getAuthorities().stream())
                .map(authority -> new SimpleGrantedAuthority(authority.getName().toString()))
                .collect(Collectors.toList());
    }

}
