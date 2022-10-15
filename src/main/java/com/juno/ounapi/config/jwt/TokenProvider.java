package com.juno.ounapi.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.juno.ounapi.dto.token.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenProvider {
    private final Environment env;

    public String createAccessToken(TokenDto tokenDto){
        String token = JWT.create()
                .withSubject(String.valueOf(tokenDto.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + Duration.ofMinutes(Long.valueOf(env.getProperty("jwt.expires-in"))).toMillis()))
                .sign(Algorithm.HMAC512(env.getProperty("jwt.access-secret")));
        return token;
    }

    public String createRefreshToken(TokenDto tokenDto){
        String token = JWT.create()
                .withSubject(String.valueOf(tokenDto.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + Duration.ofDays(Long.valueOf(env.getProperty("jwt.refresh-expires-in"))).toMillis()))
                .sign(Algorithm.HMAC512(env.getProperty("jwt.refresh-secret")));
        return token;
    }

    public String parserSubject(String token){
        return JWT.decode(token).getSubject();
    }

    public Date parserExpiresAt(String token){
        return JWT.decode(token).getExpiresAt();
    }
}
