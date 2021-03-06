package com.msa.example.auth.service;

import com.msa.example.auth.config.security.PasswordEncoderConfig;
import com.msa.example.auth.config.security.TokenValidStatus;
import com.msa.example.auth.config.security.provider.TokenProvider;
import com.msa.example.auth.domain.entity.Account;
import com.msa.example.auth.domain.entity.RefreshToken;
import com.msa.example.auth.domain.entity.Role;
import com.msa.example.auth.repository.AccountRepository;
import com.msa.example.auth.repository.RefreshTokenRepository;
import com.msa.example.auth.repository.RoleRepository;
import com.msa.example.auth.web.rest.dto.MemberRequestDto;
import com.msa.example.auth.web.rest.dto.MemberResponseDto;
import com.msa.example.auth.web.rest.dto.TokenDto;
import com.msa.example.auth.web.rest.dto.TokenRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service("AuthService")
public class AuthServiceImpl implements AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RoleRepository roleRepository;

    public AuthServiceImpl(AccountRepository accountRepository,
                           PasswordEncoderConfig passwordEncoderConfig,
                           TokenProvider tokenProvider,
                           RefreshTokenRepository refreshTokenRepository,
                           RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoderConfig = passwordEncoderConfig;
        this.tokenProvider = tokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        if (accountRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new RuntimeException("?????? ???????????? ?????? ???????????????");
        }
        Account account = memberRequestDto.toMember(passwordEncoderConfig.passwordEncoder());

        Role role = roleRepository.findByName(memberRequestDto.getRole());
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        account.setRoles(roles);
        return MemberResponseDto.of(accountRepository.save(account));
    }

//    @Override
//    @Transactional
//    public TokenDto login(MemberRequestDto memberRequestDto) {
//        // 1. Login ID/PW ??? ???????????? AuthenticationToken ??????
//        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();
////        Optional<Member> member = Optional.ofNullable(memberRepository.findByEmail(memberRequestDto.getEmail())).orElseThrow(
////                () -> new UsernameNotFoundException("user not found")
////        );
////        UsernamePasswordAuthenticationToken authenticationToken2 = new UsernamePasswordAuthenticationToken("", "", "");
//
//        // 2. ????????? ?????? (????????? ???????????? ??????) ??? ??????????????? ??????
//        // authenticate ???????????? ????????? ??? ??? CustomUserDetailsService ?????? ???????????? loadUserByUsername ???????????? ?????????
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        // 3. ?????? ????????? ???????????? JWT ?????? ??????
//        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
//
//        // 4. RefreshToken ??????
//        RefreshToken refreshToken = RefreshToken.builder()
//                .tokenKey(authentication.getName())
//                .tokenValue(tokenDto.getRefreshToken())
//                .build();
//
//        refreshTokenRepository.save(refreshToken);
//
//        // 5. ?????? ??????
//        return tokenDto;
//    }

    @Override
    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token ??????
        if (tokenProvider.validateToken(tokenRequestDto.getRefreshToken()) != TokenValidStatus.SUCCESS) {
            throw new RuntimeException("Refresh Token ??? ???????????? ????????????.");
        }

        // 2. Access Token ?????? Member ID ????????????
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. ??????????????? Member ID ??? ???????????? Refresh Token ??? ?????????
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new RuntimeException("???????????? ??? ??????????????????."));

        // 4. Refresh Token ??????????????? ??????
        if (!refreshToken.getTokenValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("????????? ?????? ????????? ???????????? ????????????.");
        }

        // 5. ????????? ?????? ??????
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. ????????? ?????? ????????????
//        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
//        refreshTokenRepository.save(newRefreshToken);

        // ?????? ??????
        return tokenDto;
    }

}
