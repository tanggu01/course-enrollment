package com.example.course.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

//AbstractAuthenticationToken: Base class for Authentication objects.
//Implementations which use this class should be immutable.
public class CustomAuthenticationToken extends AbstractAuthenticationToken { //implements Authentication

    //    private String type;
    private CustomUserDetails principal;


    /**
     * Authenticated 된 CustomUserDetail (principal) 을 가지고있는 토큰
     *
     * @param principal
     * @param authorities
     */
    public CustomAuthenticationToken(CustomUserDetails principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal; //UserDetail
        setAuthenticated(true);
    }

    @Override
    public CustomUserDetails getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() { //not used
        throw new UnsupportedOperationException();
    }

}
