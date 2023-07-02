package com.example.course.config.security.guard;

import com.example.course.src.domain.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberGuard {

    private final AuthHelper authHelper;

    public boolean check() {
        return authHelper.isAuthenticated() && hasAuthority();
    }

    private boolean hasAuthority() {
        Set<UserRole> memberRoles = authHelper.extractMemberRoles();
        return memberRoles.contains(UserRole.STUDENT) || memberRoles.contains(UserRole.ADMIN) || memberRoles.contains(UserRole.PROFESSOR);
    }
}
