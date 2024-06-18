package com.fastcampus.springsecurity.config;

import com.fastcampus.springsecurity.service.JwtService;
import com.fastcampus.springsecurity.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter { // 한번만 실행되는 필터

    @Autowired private JwtService jwtService;
    @Autowired private UserService userService;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO: jwt 검증
        String BEARER_PREFIX="Bearer ";
        var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        var securityContext = SecurityContextHolder.getContext(); // 인증정보 저장소

        if(ObjectUtils.isEmpty(authorization) && authorization.startsWith(BEARER_PREFIX)&&
        securityContext.getAuthentication()==null) {
            var accessToken = authorization.substring(BEARER_PREFIX.length());// accessToken 값 추출
            String username = jwtService.getUsername(accessToken);// jwt token에서 username 추출
            var userDetails = userService.loadUserByUsername(username);// username으로 user 정보 조회 // 사용자 정보
            var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());// 사용자 정보로 authentication 객체 생성


            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));// authentication 객체에 요청정보 저장
            securityContext.setAuthentication(authenticationToken);// securityContext에 authentication 객체 저장
            SecurityContextHolder.setContext(securityContext);// securityContext 저장소에 저장
        }

        filterChain.doFilter(request, response);

    }
}
