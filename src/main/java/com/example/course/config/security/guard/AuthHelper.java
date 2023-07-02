package com.example.course.config.security.guard;

import com.example.course.config.security.CustomAuthenticationToken;
import com.example.course.config.security.CustomUserDetails;
import com.example.course.src.domain.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
@Slf4j
public class AuthHelper {

    public boolean isAuthenticated() {// return true if the token has been authenticated and the AbstractSecurityInterceptor does not need to present the token to the AuthenticationManager again for re-authentication.
        return getAuthentication() instanceof CustomAuthenticationToken && getAuthentication().isAuthenticated(); //Authentication.isAuthenticated()
    }

    public Set<UserRole> extractMemberRoles() {
        return getUserDetails().getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .map(strAuth -> UserRole.valueOf(strAuth))
                .collect(Collectors.toSet());
    }

    private CustomUserDetails getUserDetails() {
        return (CustomUserDetails) getAuthentication().getPrincipal();
    }
    private Authentication getAuthentication() { //obtains the currently authenticated principal, or an authentication request token
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
