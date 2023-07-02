package com.example.course.config.security;

import com.example.course.utils.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;


    /**
     * Causes the next filter in the chain to be invoked, or if the calling filter is the last filter in the chain, causes the resource at the end of the chain to be invoked.
     * guaranteed to be just invoked once per request within a single request thread
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Optional<String> t = extractToken(request); //resolveToken

        if (!(t.isEmpty())) {
            Authentication authentication = tokenService.validateToken(request, t.get()); //토큰 검증 - 유효 여부 확인. Authentication 이 true인 AuthToken 반환
            //Changes the currently authenticated principal, or removes the authentication information.
            //Params: authentication – the new Authentication token, or null if no further authentication information should be stored
            SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContextHolder 에 넣을 유저정보(Authentication 객체)를 받아오기
        } else {
            request.setAttribute("exception", "토큰 헤더가 잘못되었습니다. Authorization 을 넣어주세요.");
        }
        chain.doFilter(request, response);
    }


    private Optional<String> extractToken(ServletRequest request) {
        //getHeader(): return a String containing the value of the requested header, or null if the request does not have a header of that name
        return Optional.ofNullable(((HttpServletRequest) request).getHeader("Authorization"));
    }
}

