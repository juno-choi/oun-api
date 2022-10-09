package com.juno.ounapi.service.kakao;

import com.juno.ounapi.common.httpclient.MyHttpClient;
import com.juno.ounapi.domain.member.Member;
import com.juno.ounapi.dto.kakao.OauthRequest;
import com.juno.ounapi.exception.CommonException;
import com.juno.ounapi.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OauthServiceImplUnitTest {
    @InjectMocks
    private OauthServiceImpl oauthService;

    @Mock
    private MyHttpClient myHttpClient;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 조회에 실패한다.")
    void oauthTokenFail1(){
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
                return "{\"id\":2469559669,\"connected_at\":\"2022-10-03T06:24:11Z\",\"properties\":{\"nickname\":\"최준호\",\"profile_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"thumbnail_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"최준호\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"is_default_image\":false},\"has_email\":true,\"email_needs_agreement\":false,\"is_email_valid\":true,\"is_email_verified\":true,\"email\":\"ililil9482@naver.com\"}}";
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

        // when
        CommonException ex = assertThrows(CommonException.class, () -> oauthService.oauthToken(request));

        // then
        assertEquals("회원 조회 실패", ex.getMessage());
    }


    @Test
    @DisplayName("회원 조회에 성공한다.")
    void oauthTokenSuccess(){
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
                        return "{\"id\":2469559669,\"connected_at\":\"2022-10-03T06:24:11Z\",\"properties\":{\"nickname\":\"최준호\",\"profile_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"thumbnail_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"최준호\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"is_default_image\":false},\"has_email\":true,\"email_needs_agreement\":false,\"is_email_valid\":true,\"is_email_verified\":true,\"email\":\"ililil9482@naver.com\"}}";
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
        Member member = new Member(1L, "kakao", "123456", null, null, "최준호", null, null, "profile image", "thumbnail image", LocalDateTime.now(), LocalDateTime.now());
        given(memberRepository.findByMemberId(any())).willReturn(Optional.of(member));

        // when
        oauthService.oauthToken(request);

        // then

    }
}