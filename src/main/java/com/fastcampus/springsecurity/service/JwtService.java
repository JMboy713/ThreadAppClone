package com.fastcampus.springsecurity.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Slf4j
@Service
public class JwtService {

    private static final SecretKey key = Jwts.SIG.HS256.key().build();
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername());
    }// jwt token 생성


    public String getUsername(String accessToken){
        return getSubject(accessToken);
    }


    private String generateToken(String subject) {
        var now = new Date();
        var exp = new Date(now.getTime() + (1000 * 60 * 60 * 3));
        return Jwts.builder().setSubject(subject).signWith(key)
                .issuedAt(now)
                .expiration(exp)
                .compact();
    }// jwt token 생성

    private String getSubject(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
        }catch(JwtException e){
            logger.error("JwtException", e);
            throw e;
        }

    }// jwt token에서 subject 추출
}
