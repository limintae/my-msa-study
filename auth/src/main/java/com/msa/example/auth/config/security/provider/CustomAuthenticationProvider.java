package com.msa.example.auth.config.security.provider;

import com.msa.example.auth.domain.CustomUserDetailsService;
import com.msa.example.auth.domain.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    public CustomAuthenticationProvider(
            PasswordEncoder passwordEncoder,
            CustomUserDetailsService customUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication == null){
            throw new IllegalArgumentException("authentication 발급 오류");
        }

        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        MemberInfo memberInfo = (MemberInfo) customUserDetailsService.loadUserByUsername(email);
        if(!passwordEncoder.matches(password, memberInfo.getPassword())){
            throw new BadCredentialsException("패스워드가 일치하지 않습니다.");
        }

        return new UsernamePasswordAuthenticationToken(memberInfo, null, memberInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
