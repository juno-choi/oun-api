package com.juno.ounapi.service.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juno.ounapi.common.httpclient.MyHttpClient;
import com.juno.ounapi.config.jwt.TokenProvider;
import com.juno.ounapi.config.mapper.MyObjectMapper;
import com.juno.ounapi.domain.member.Member;
import com.juno.ounapi.dto.kakao.OauthRequest;
import com.juno.ounapi.dto.kakao.user.KakaoAccount;
import com.juno.ounapi.dto.kakao.user.KakaoResponse;
import com.juno.ounapi.dto.kakao.user.Profile;
import com.juno.ounapi.dto.kakao.user.Properties;
import com.juno.ounapi.enums.api.oauth.OauthFailMsg;
import com.juno.ounapi.enums.oauth.Oauth;
import com.juno.ounapi.exception.CommonException;
import com.juno.ounapi.repository.member.MemberRepository;
import com.juno.ounapi.vo.kakao.OauthResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class OauthServiceImplUnitTest {
    @InjectMocks
    private OauthServiceImpl oauthService;

    @Mock
    private MyHttpClient myHttpClient;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations valueOperations;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private MyObjectMapper objectMapper;

    @Test
    @DisplayName("?????? ????????? ????????????.")
    void oauthTokenFail1() throws JsonProcessingException {
        // given
        OauthRequest request = new OauthRequest("access_token", "bearer", "refresh_token", 21599L, "account_email profile_image profile_nickname", 5183999L);
        given(myHttpClient.httpPostRequest(any()))
                .willReturn(new HttpResponse<String>() {
            @Override
            public int statusCode() {
                return 200;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {
                return "{\"id\":2469559669,\"connected_at\":\"2022-10-03T06:24:11Z\",\"properties\":{\"nickname\":\"?????????\",\"profile_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"thumbnail_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"?????????\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"is_default_image\":false},\"has_email\":true,\"email_needs_agreement\":false,\"is_email_valid\":true,\"is_email_verified\":true,\"email\":\"ililil9482@naver.com\"}}";
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return HttpClient.Version.HTTP_1_1;
            }
        });
        given(memberRepository.findByMemberId(any())).willReturn(Optional.empty());
        given(objectMapper.readValue(anyString(), (Class<Object>) any())).willReturn(new KakaoResponse(1L, null, new Properties("tester", "", ""), new KakaoAccount(null, null, new Profile("","thumnail-url", "image-url", "default-image-url"), null, null, null, null, "test@mail.com")));
        // when
        CommonException ex = assertThrows(CommonException.class, () -> oauthService.oauthToken(request));

        // then
        assertEquals(OauthFailMsg.KAKAO_LOGIN_FAIL.message, ex.getResultMsg());
    }


    @Test
    @DisplayName("?????? ????????? ????????????.")
    void oauthTokenSuccess() throws JsonProcessingException {
        // given
        OauthRequest request = new OauthRequest("kakao-access-token", "bearer", "kakao-refresh-token", 21599L, "account_email profile_image profile_nickname", 5183999L);
        given(myHttpClient.httpPostRequest(any()))
                .willReturn(new HttpResponse<String>() {
                    @Override
                    public int statusCode() {
                        return 200;
                    }

                    @Override
                    public HttpRequest request() {
                        return null;
                    }

                    @Override
                    public Optional<HttpResponse<String>> previousResponse() {
                        return Optional.empty();
                    }

                    @Override
                    public HttpHeaders headers() {
                        return null;
                    }

                    @Override
                    public String body() {
                        return "{\"id\":2469559669,\"connected_at\":\"2022-10-03T06:24:11Z\",\"properties\":{\"nickname\":\"?????????\",\"profile_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"thumbnail_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"?????????\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"is_default_image\":false},\"has_email\":true,\"email_needs_agreement\":false,\"is_email_valid\":true,\"is_email_verified\":true,\"email\":\"ililil9482@naver.com\"}}";
                    }

                    @Override
                    public Optional<SSLSession> sslSession() {
                        return Optional.empty();
                    }

                    @Override
                    public URI uri() {
                        return null;
                    }

                    @Override
                    public HttpClient.Version version() {
                        return HttpClient.Version.HTTP_1_1;
                    }
                });
        Member member = new Member(1L, Oauth.KAKAO, "123456", "ililil9482@naver.com", null, "?????????", null, null, "profile image", "thumbnail image", LocalDateTime.now(), LocalDateTime.now());
        given(memberRepository.findByMemberId(any())).willReturn(Optional.of(member));
        given(redisTemplate.opsForValue()).willReturn(valueOperations);

        given(tokenProvider.createAccessToken(any())).willReturn("access-token");
        given(tokenProvider.createRefreshToken(any())).willReturn("refresh-token");
        given(tokenProvider.parserExpiresAt(any())).willReturn(new Date());
        given(objectMapper.readValue(anyString(), (Class<Object>) any())).willReturn(new KakaoResponse(1L, null, new Properties("tester", "", ""), new KakaoAccount(null, null, new Profile("","thumnail-url", "image-url", "default-image-url"), null, null, null, null, "test@mail.com")));
        doNothing().when(valueOperations).set(anyString(), any(), any());

        // when
        OauthResponse oauthResponse = oauthService.oauthToken(request);

        // then
        assertEquals(member.getEmail(), oauthResponse.getEmail());
    }


    @Test
    @DisplayName("??????????????? ????????????.")
    void oauthJoinSuccess() throws JsonProcessingException {
        // given
        OauthRequest request = new OauthRequest("kakao-access-token", "bearer", "kakao-refresh-token", 21599L, "account_email profile_image profile_nickname", 5183999L);
        given(myHttpClient.httpPostRequest(any()))
                .willReturn(new HttpResponse<String>() {
                    @Override
                    public int statusCode() {
                        return 200;
                    }

                    @Override
                    public HttpRequest request() {
                        return null;
                    }

                    @Override
                    public Optional<HttpResponse<String>> previousResponse() {
                        return Optional.empty();
                    }

                    @Override
                    public HttpHeaders headers() {
                        return null;
                    }

                    @Override
                    public String body() {
                        return "{\"id\":2469559669,\"connected_at\":\"2022-10-03T06:24:11Z\",\"properties\":{\"nickname\":\"?????????\",\"profile_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"thumbnail_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"?????????\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"is_default_image\":false},\"has_email\":true,\"email_needs_agreement\":false,\"is_email_valid\":true,\"is_email_verified\":true,\"email\":\"ililil9482@naver.com\"}}";
                    }

                    @Override
                    public Optional<SSLSession> sslSession() {
                        return Optional.empty();
                    }

                    @Override
                    public URI uri() {
                        return null;
                    }

                    @Override
                    public HttpClient.Version version() {
                        return HttpClient.Version.HTTP_1_1;
                    }
                });
        Member member = new Member(1L, Oauth.KAKAO, "123456", "ililil9482@naver.com", null, "?????????", null, null, "profile image", "thumbnail image", LocalDateTime.now(), LocalDateTime.now());
        given(memberRepository.save(any())).willReturn(member);
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(tokenProvider.createAccessToken(any())).willReturn("access-token");
        given(tokenProvider.createRefreshToken(any())).willReturn("refresh-token");
        given(tokenProvider.parserExpiresAt(any())).willReturn(new Date());

        given(objectMapper.readValue(anyString(), (Class<Object>) any())).willReturn(new KakaoResponse(1L, null, new Properties("tester", "", ""), new KakaoAccount(null, null, new Profile("","thumnail-url", "image-url", "default-image-url"), null, null, null, null, "test@mail.com")));

        doNothing().when(valueOperations).set(anyString(), any(), any());

        // when
        OauthResponse oauthResponse = oauthService.oauthJoin(request);

        // then
        assertEquals(member.getEmail(), oauthResponse.getEmail());
    }
}