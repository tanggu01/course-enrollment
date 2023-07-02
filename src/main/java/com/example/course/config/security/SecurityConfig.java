package com.example.course.config.security;

import com.example.course.utils.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                //form login 비활성화
                .formLogin().disable()
                //csrf 관련 정책 비활성화
                .csrf().disable()
                //세션 관리정책 설정, 세션을 유지하지 않도록 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //회원가입/로그인 프로세스 - permitAll
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/professors/**").permitAll()
                .antMatchers(HttpMethod.POST, "/admins/**").permitAll()
                .antMatchers(HttpMethod.POST, "/students/**").permitAll()
                ///courses
                .antMatchers(HttpMethod.GET, "/courses").access("@memberGuard.check()")
                .antMatchers(HttpMethod.POST, "/courses").access("@adminGuard.check()")
                .antMatchers(HttpMethod.GET, "/courses/students").access("@studentGuard.check()")
                .antMatchers(HttpMethod.PATCH, "/courses/professors/**").access("@professorGuard.check()")
                .antMatchers(HttpMethod.GET, "/courses/professors/**").access("@professorGuard.check()")
                .antMatchers(HttpMethod.POST, "/courses/admin").access("@adminGuard.check()")
                //phase
                .antMatchers(HttpMethod.POST, "/phase/**").access("@adminGuard.check()")
                .antMatchers(HttpMethod.PUT, "/phase/**").access("@adminGuard.check()")
                //student-course
                .antMatchers(HttpMethod.POST, "/student-course/**").access("@studentGuard.check()")
                .antMatchers(HttpMethod.DELETE, "/student-course/**").access("@studentGuard.check()")
//                .antMatchers("/member/**").access("@memberGuard.check()")
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("*/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
