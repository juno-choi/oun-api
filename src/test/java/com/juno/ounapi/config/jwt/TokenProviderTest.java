package com.juno.ounapi.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juno.ounapi.dto.token.TokenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TokenProviderTest {
    @InjectMocks
    private TokenProvider tokenProvider;

    @Mock
    private Environment env;

    @Test
    @DisplayName("access token 생성에 성공한다.")
    void accessTokenCreateSuccess(){
        // given & when
        given(env.getProperty(eq("jwt.expires-in"))).willReturn("15");
        given(env.getProperty(eq("jwt.access-secret"))).willReturn("access-secret");

        String accessToken = tokenProvider.createAccessToken(TokenDto.builder().id(1L).build());
        // then
        assertNotNull(accessToken);
        System.out.println("access token =" + accessToken);
    }


    @Test
    @DisplayName("refresh token 생성에 성공한다.")
    void refreshTokenCreateSuccess(){
        // given & when
        given(env.getProperty(eq("jwt.refresh-expires-in"))).willReturn("30");
        given(env.getProperty(eq("jwt.refresh-secret"))).willReturn("refresh-secret");

        String refreshToken = tokenProvider.createRefreshToken(TokenDto.builder().id(1L).build());
        // then
        assertNotNull(refreshToken);
        System.out.println("refreshToken = " + refreshToken);
    }

    @Test
    @DisplayName("token parser 성공한다.")
    void parserTokenSuccess() throws JsonProcessingException {
        // given
        Long id = 3L;
        given(env.getProperty(eq("jwt.expires-in"))).willReturn("15");
        given(env.getProperty(eq("jwt.access-secret"))).willReturn("access-secret");
        String accessToken = tokenProvider.createAccessToken(TokenDto.builder().id(id).build());

        // when
        String parser = tokenProvider.parserSubject(accessToken);
        // then
        assertTrue(id == Long.valueOf(parser));
        System.out.println("parser = " + parser);
    }
}