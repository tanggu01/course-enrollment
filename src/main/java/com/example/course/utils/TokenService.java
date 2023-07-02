package com.example.course.utils;

import com.example.course.common.exception.BadRequestException;
import com.example.course.config.handler.JwtHandler;
import com.example.course.config.security.CustomAuthenticationToken;
import com.example.course.config.security.CustomUserDetails;
import com.example.course.config.security.CustomUserDetailsService;
import com.example.course.src.domain.UserRole;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtHandler jwtHandler;
    private final CustomUserDetailsService customUserDetailsService;


    @Value("${jwt.max-age.access}")
    private Long accessTokenMaxAgeSeconds;


    @Value("${jwt.key.access}") // 3
    private String accessKey;

    private static final String ROLE_TYPES = "ROLE_TYPES";
    private static final String MEMBER_ID = "MEMBER_ID";

    @Getter
    @AllArgsConstructor
    public static class PrivateClaims {
        private String memberId;
        private UserRole roleTypes;
    }

    public String createAccessToken(PrivateClaims privateClaims) {
        return jwtHandler.createToken(accessKey,
                Map.of(MEMBER_ID, privateClaims.getMemberId(), ROLE_TYPES, privateClaims.getRoleTypes()),
                accessTokenMaxAgeSeconds);
    }

    private PrivateClaims convert(Claims claims) {
        return new PrivateClaims(claims.get(MEMBER_ID, String.class), UserRole.valueOf(claims.get(ROLE_TYPES, String.class)));
    }

    /**
     * validate ACCESS Token. doFilter 에서 쓰인다
     */
    public Authentication validateToken(HttpServletRequest request, String token) throws BadRequestException {
        String exception = "exception";
        try {
            Jwts.parser().setSigningKey(accessKey.getBytes()).parseClaimsJws(jwtHandler.untype(token));
            return getAuthentication(token);
            //loadByUserName 후 Authentication 형식인 CustomAuthenticationToken 반환 !!
        } catch (BadRequestException e) {
            request.setAttribute(exception, "토큰을 입력해주세요. (앞에 'Bearer ' 포함)");
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException e) {
            request.setAttribute(exception, "잘못된 토큰입니다."); //토큰의 형식을 확인하세요. Bearer 없음
        } catch (ExpiredJwtException e) {
            request.setAttribute(exception, "토큰이 만료되었습니다.");
        } catch (IllegalArgumentException e) { //Authorization, Bearer
            request.setAttribute(exception, "토큰을 입력해주세요.");
        } catch (JwtException e) {
            e.printStackTrace();
            request.setAttribute(exception, "토큰을 확인해주세요."); //추가
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(exception, "general exception"); //추가
        }
        return null;
    }

    /**
     * loadUserByUsername 으로 UserDetail 반환
     * CustomAuthenticationToken 반환!!
     */
    private Authentication getAuthentication(String token) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(token);
        return new CustomAuthenticationToken(userDetails, userDetails.getAuthorities()); //(principal, authorities)
    }
}
