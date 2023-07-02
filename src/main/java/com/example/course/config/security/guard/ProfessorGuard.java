package com.example.course.config.security.guard;

import com.example.course.src.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ProfessorGuard {

    private final AuthHelper authHelper;

    public boolean check() {
        return authHelper.isAuthenticated() && hasAuthority();
    }

    private boolean hasAuthority() {
        Set<UserRole> memberRoles = authHelper.extractMemberRoles();
        return memberRoles.contains(UserRole.PROFESSOR);
    }
}
