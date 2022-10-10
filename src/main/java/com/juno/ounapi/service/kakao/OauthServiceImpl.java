package com.juno.ounapi.service.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.juno.ounapi.common.Error;
import com.juno.ounapi.common.httpclient.MyHttpClient;
import com.juno.ounapi.common.httpclient.vo.PostRequest;
import com.juno.ounapi.domain.member.Member;
import com.juno.ounapi.dto.kakao.user.KakaoResponse;
import com.juno.ounapi.dto.kakao.OauthRequest;
import com.juno.ounapi.enums.api.CommonExceptionCode;
import com.juno.ounapi.enums.oauth.Oauth;
import com.juno.ounapi.exception.CommonException;
import com.juno.ounapi.repository.member.MemberRepository;
import com.juno.ounapi.vo.kakao.EmptyResponse;
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
    private final MemberRepository memberRepository;

    @Override
    public OauthResponse oauthToken(OauthRequest oauthRequest) {
        log.debug("oauthToken call ...");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        KakaoResponse kakaoResponse = null;
        String accessToken = oauthRequest.getAccess_token();
        String expiresIn = oauthRequest.getExpires_in().toString();
        String memberId = "";

        // 사용자 정보 가져오기
        HttpResponse<String> response = myHttpClient.httpPostRequest(
                PostRequest.builder()
                        .uri("https://kapi.kakao.com/v2/user/me")
                        .headers(new String[]{"Content-type", "application/x-www-form-urlencoded;charset=utf-8", "Authorization", String.format("Bearer %s", accessToken)})
                        .body("")
                        .build()
        );
        log.debug("response status = {}", response.statusCode());

        try {
            kakaoResponse = objectMapper.readValue(response.body(), KakaoResponse.class);
        } catch (JsonProcessingException e) {
            log.error("json parser error",e);
        }
        memberId = kakaoResponse.getId().toString();

        // 회원 조회
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new CommonException(CommonExceptionCode.LOGIN_FAIL, Error.builder()
                        .error(new EmptyResponse())
                        .build())
        );

        // redis에 사용자 토큰 등록하기

        // db에 refresh_token 저장하기

        return OauthResponse.builder()
                .nickname(member.getNickname())
                .access_token(accessToken)
                .access_token_expires(expiresIn)
                .email(member.getEmail())
                .profile_img(member.getProfile())
                .thumbnail_img(member.getThumbnail())
                .build();
    }

    @Override
    public OauthResponse oauthJoin(OauthRequest oauthRequest) {
        log.debug("oauthJoin call ...");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        KakaoResponse kakaoResponse = null;
        String accessToken = oauthRequest.getAccess_token();
        String expiresIn = oauthRequest.getExpires_in().toString();
        String memberId = "";

        // 사용자 정보 가져오기
        HttpResponse<String> response = myHttpClient.httpPostRequest(
                PostRequest.builder()
                        .uri("https://kapi.kakao.com/v2/user/me")
                        .headers(new String[]{"Content-type", "application/x-www-form-urlencoded;charset=utf-8", "Authorization", String.format("Bearer %s", accessToken)})
                        .body("")
                        .build()
        );
        log.debug("response status = {}", response.statusCode());

        try {
            kakaoResponse = objectMapper.readValue(response.body(), KakaoResponse.class);
        } catch (JsonProcessingException e) {
            log.error("json parser error",e);
        }
        memberId = kakaoResponse.getId().toString();
        String email = kakaoResponse.getKakao_account().getEmail();
        String nickname = kakaoResponse.getProperties().getNickname();
        String profile = kakaoResponse.getKakao_account().getProfile().getProfile_image_url();
        String thumbnail = kakaoResponse.getKakao_account().getProfile().getThumbnail_image_url();
        log.debug("member id = {}",memberId);
        // 회원 가입
        Member member = memberRepository.save(Member.builder()
                .email(email)
                .memberId(memberId)
                .nickname(nickname)
                .type(Oauth.KAKAO.name())
                .profile(profile)
                .thumbnail(thumbnail)
                .build());

        log.debug("회원 가입 완료 = {}", member.getId());

        // redis에 사용자 토큰 등록하기

        // db에 refresh_token 저장하기

        return OauthResponse.builder()
                .nickname(nickname)
                .access_token(accessToken)
                .access_token_expires(expiresIn)
                .email(email)
                .profile_img(profile)
                .thumbnail_img(thumbnail)
                .build();
    }


}
