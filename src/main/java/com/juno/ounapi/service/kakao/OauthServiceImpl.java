package com.juno.ounapi.service.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.juno.ounapi.common.Error;
import com.juno.ounapi.common.httpclient.MyHttpClient;
import com.juno.ounapi.common.httpclient.vo.PostRequest;
import com.juno.ounapi.config.jwt.TokenProvider;
import com.juno.ounapi.domain.member.Member;
import com.juno.ounapi.dto.kakao.user.KakaoResponse;
import com.juno.ounapi.dto.kakao.OauthRequest;
import com.juno.ounapi.dto.token.TokenDto;
import com.juno.ounapi.enums.api.ResultCode;
import com.juno.ounapi.enums.api.ResultType;
import com.juno.ounapi.enums.api.oauth.OauthFailMsg;
import com.juno.ounapi.enums.oauth.Oauth;
import com.juno.ounapi.exception.CommonException;
import com.juno.ounapi.repository.member.MemberRepository;
import com.juno.ounapi.vo.kakao.TempResponse;
import com.juno.ounapi.vo.kakao.OauthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.time.Duration;

@RequiredArgsConstructor
@Service
@Slf4j
public class OauthServiceImpl implements OauthService{
    private final MyHttpClient myHttpClient;
    private final MemberRepository memberRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final TokenProvider tokenProvider;

    @Override
    public OauthResponse oauthToken(OauthRequest oauthRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        KakaoResponse kakaoResponse = null;
        String kakaoAccessToken = oauthRequest.getAccess_token();
        String kakaoRefreshToken = oauthRequest.getRefresh_token();

        String memberId = "";

        // 사용자 정보 가져오기
        HttpResponse<String> response = myHttpClient.httpPostRequest(
                PostRequest.builder()
                        .uri("https://kapi.kakao.com/v2/user/me")
                        .headers(new String[]{"Content-type", "application/x-www-form-urlencoded;charset=utf-8", "Authorization", String.format("Bearer %s", kakaoAccessToken)})
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
                () -> new CommonException(HttpStatus.BAD_REQUEST, ResultCode.BAD_REQUEST, ResultType.ALERT, OauthFailMsg.KAKAO_LOGIN_FAIL.message, Error.builder()
                        .error(new TempResponse())
                        .build())
        );

        // token 생성
        return getOauthResponse(kakaoAccessToken, kakaoRefreshToken, member);
    }

    @Override
    public OauthResponse oauthJoin(OauthRequest oauthRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        KakaoResponse kakaoResponse = null;
        String kakaoAccessToken = oauthRequest.getAccess_token();
        String kakaoRefreshToken = oauthRequest.getRefresh_token();
        String memberId = "";
        
        // 사용자 정보 가져오기
        HttpResponse<String> response = myHttpClient.httpPostRequest(
                PostRequest.builder()
                        .uri("https://kapi.kakao.com/v2/user/me")
                        .headers(new String[]{"Content-type", "application/x-www-form-urlencoded;charset=utf-8", "Authorization", String.format("Bearer %s", kakaoAccessToken)})
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
                .type(Oauth.KAKAO)
                .profile(profile)
                .thumbnail(thumbnail)
                .build());

        log.debug("회원 가입 완료 = {}", member.getId());


        // token 생성
        return getOauthResponse(kakaoAccessToken, kakaoRefreshToken, member);
    }

    private OauthResponse getOauthResponse(String kakaoAccessToken, String kakaoRefreshToken, Member member) {
        
        String accessToken = tokenProvider.createAccessToken(TokenDto.builder()
                .id(member.getId())
                .build());
        String accessTokenExpiresIn = String.valueOf(tokenProvider.parserExpiresAt(accessToken)
                .getTime());
        // refresh
        String refreshToken = tokenProvider.createRefreshToken(TokenDto.builder()
                .id(member.getId())
                .build());
        String refreshTokenExpiresIn = String.valueOf(tokenProvider.parserExpiresAt(refreshToken)
                .getTime());

        log.debug("kakao access = {}, kakao refresh ={}", kakaoAccessToken, kakaoRefreshToken);
        log.debug("oun access = {}, oun refresh ={}", accessToken, refreshToken);

        // redis에 사용자 토큰 등록하기
        String memberSeq = String.valueOf(member.getId());
        ValueOperations<String, Object> redis = redisTemplate.opsForValue();
        redis.set(accessToken, memberSeq, Duration.ofMillis(Long.valueOf(accessTokenExpiresIn)));
        redis.set(refreshToken, memberSeq, Duration.ofMillis(Long.valueOf(refreshTokenExpiresIn)));

        // db에 refresh_token 저장하기

        return OauthResponse.builder()
                .nickname(member.getNickname())
                .access_token(accessToken)
                .access_token_expires(accessTokenExpiresIn)
                .refresh_token(refreshToken)
                .refresh_token_expires(refreshTokenExpiresIn)
                .email(member.getEmail())
                .profile_img(member.getProfile())
                .thumbnail_img(member.getThumbnail())
                .build();
    }
}
