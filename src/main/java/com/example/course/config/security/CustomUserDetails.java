package com.example.course.config.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

//Provides core user information.
//Implementations are not used directly by Spring Security for security purposes.
// They simply store user information which is later encapsulated into Authentication objects.
// This allows non-security related user information (such as email addresses, telephone numbers etc) to be stored in a convenient location.
@Getter
public class CustomUserDetails implements UserDetails {

    private final String userId;
    private final Set<GrantedAuthority> authorities;

    public CustomUserDetails(String userId, SimpleGrantedAuthority authoritie) {
        this.userId = userId;
        this.authorities = Set.of(authoritie);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAccountNonLocked() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEnabled() {
        throw new UnsupportedOperationException();
    }
}
