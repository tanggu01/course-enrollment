package com.example.course.config.handler;

import com.example.course.common.exception.BadRequestException;
import com.example.course.common.exception.CustomException;
import com.example.course.src.domain.UserRole;
import com.example.course.utils.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtHandler {

    private String type = "Bearer";


    @Value("${jwt.key.access}") // parse 할때
    private String accessKey;


    public Long getMemberId() throws CustomException {
        //1. JWT 추출
        String accessToken = untype(getJwt());
        // 2. userIdx 추출
        return Long.valueOf(
                Jwts.parser()
                        .setSigningKey(accessKey.getBytes())
                        .parseClaimsJws(accessToken)
                        .getBody()
                        .get("MEMBER_ID", String.class));
    }

    public String getJwt(){ //resolveToken
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }


    /**
     * PrivateClaims으로 토큰 생성
     */
    public String createToken(String key, Map<String, Object> privateClaims, long maxAgeSeconds) {
        Date now = new Date();
        return type + " " + Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + maxAgeSeconds * 1000L))
                .addClaims(privateClaims)
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();
    }

    public Optional<TokenService.PrivateClaims> createPrivateClaim(String token) {
        Optional<Claims> claims1 = parseAccessToken(accessKey, token); //토큰 파싱
        return claims1.map(claims -> convertClaim(claims)); //
    }

    public Optional<Claims> parseAccessToken(String key, String token) {
        try {
            return Optional.of(Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(untype(token)).getBody());
        } catch (JwtException e) {
            return Optional.empty();
        }
    }
    private TokenService.PrivateClaims convertClaim(Claims claims) {
        return new TokenService.PrivateClaims(claims.get("MEMBER_ID", String.class), UserRole.valueOf(claims.get("ROLE_TYPES", String.class)));
    }


    public String untype(String token) throws BadRequestException{
        if (token.length() < 6) {
            throw new BadRequestException("토큰을 입력해주세요.");
        }
        return token.substring(type.length());
    }
}
