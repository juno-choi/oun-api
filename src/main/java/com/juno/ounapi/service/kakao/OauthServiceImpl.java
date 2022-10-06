package com.juno.ounapi.service.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.juno.ounapi.common.httpclient.MyHttpClient;
import com.juno.ounapi.common.httpclient.vo.PostRequest;
import com.juno.ounapi.dto.kakao.user.KakaoResponse;
import com.juno.ounapi.dto.kakao.OauthRequest;
import com.juno.ounapi.vo.kakao.OauthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@RequiredArgsConstructor
@Service
@Slf4j
public class OauthServiceImpl implements OauthService{
    private final MyHttpClient myHttpClient;

    @Override
    public OauthResponse oauthToken(OauthRequest oauthRequest) {
        log.debug("oauthToken call ...");
        KakaoResponse kakaoResponse = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // 사용자 정보 가져오기
        HttpResponse<String> response = myHttpClient.httpPostRequest(
                PostRequest.builder()
                        .uri("https://kapi.kakao.com/v2/user/me")
                        .headers(new String[]{"Content-type", "application/x-www-form-urlencoded;charset=utf-8", "Authorization", String.format("Bearer %s", oauthRequest.getAccess_token())})
                        .body("")
                        .build()
        );

        try {
            kakaoResponse = objectMapper.readValue(response.body(), KakaoResponse.class);
        } catch (JsonProcessingException e) {
            log.error("json parser error",e);
        }
        log.debug("response status = {}", response.statusCode());

        log.debug(kakaoResponse.getId().toString());

        // redis에 사용자 토큰 등록하기

        // db에 refresh_token 저장하기

        return OauthResponse.builder()
                .nickname(kakaoResponse.getKakao_account()
                        .getProfile()
                        .getNickname()
                ).access_token(oauthRequest.getAccess_token())
                .access_token_expires(oauthRequest.getExpires_in().toString())
                .email(kakaoResponse.getKakao_account().getEmail())
                .profile_img(kakaoResponse.getKakao_account().getProfile().getProfile_image_url())
                .thumbnail_img(kakaoResponse.getKakao_account().getProfile().getThumbnail_image_url())
                .build();
    }


}
