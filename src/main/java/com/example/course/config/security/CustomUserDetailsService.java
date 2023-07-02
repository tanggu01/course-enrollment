package com.example.course.config.security;

import com.example.course.config.handler.JwtHandler;
import com.example.course.utils.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final JwtHandler jwtHandler;


    @Override
    public CustomUserDetails loadUserByUsername(String parsedToken) throws UsernameNotFoundException {
        return convertTokenToUserDetail(parsedToken);
    }


    /**
     * JWT 에서 Claim 추출 후 >>인증된<< !! CustomUserDetail 반환
     *
     * @param parsedToken
     * @return
     */
    private CustomUserDetails convertTokenToUserDetail(String parsedToken) {
        Optional<TokenService.PrivateClaims> privateClaims = jwtHandler.createPrivateClaim(parsedToken);
        return new CustomUserDetails(privateClaims.get().getMemberId(), new SimpleGrantedAuthority(privateClaims.get().getRoleTypes().toString()));
    }

}
