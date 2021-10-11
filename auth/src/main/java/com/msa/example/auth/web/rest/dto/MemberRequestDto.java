package com.msa.example.auth.web.rest.dto;

import com.msa.example.auth.domain.entity.Account;
import com.msa.example.auth.domain.enums.RoleStatus;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {

    private String email;
    private String password;
    private String name;
    private RoleStatus role;

    public Account toMember(PasswordEncoder passwordEncoder) {
        return Account.builder()
                .email(this.email)
                .name(this.name)
                .password(passwordEncoder.encode(this.password))
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
