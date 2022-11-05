package com.juno.ounapi.service.kakao;

import com.juno.ounapi.common.httpclient.MyHttpClient;
import com.juno.ounapi.domain.member.Member;
import com.juno.ounapi.dto.kakao.OauthRequest;
import com.juno.ounapi.enums.api.ResultCode;
import com.juno.ounapi.enums.api.oauth.OauthFailMsg;
import com.juno.ounapi.enums.oauth.Oauth;
import com.juno.ounapi.exception.CommonException;
import com.juno.ounapi.repository.member.MemberRepository;
import com.juno.ounapi.vo.kakao.OauthResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
public class OauthServiceImplTest {
    @Autowired
    private OauthService oauthService;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private MyHttpClient myHttpClient;


    @Test
    @DisplayName("회원가입되지 않은 회원은 로그인에 실패한다.")
    public void oauthFail1() throws Exception {
        //given
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
                        return "{\"id\":1111111,\"connected_at\":\"2022-10-03T06:24:11Z\",\"properties\":{\"nickname\":\"최준호\",\"profile_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"thumbnail_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"최준호\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"is_default_image\":false},\"has_email\":true,\"email_needs_agreement\":false,\"is_email_valid\":true,\"is_email_verified\":true,\"email\":\"ililil9482@naver.com\"}}";
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
        //when
        CommonException ex = assertThrows(CommonException.class, () -> oauthService.oauthToken(request));
        //then
        assertEquals(ResultCode.BAD_REQUEST, ex.getResultCode());
        assertEquals(OauthFailMsg.KAKAO_LOGIN_FAIL.message, ex.getResultMsg());
    }

    @Test
    @DisplayName("로그인에 성공한다.")
    public void oauthSuccess() throws Exception {
        //given
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
        String nickName = "테스터";
        memberRepository.save(Member.builder()
                .memberId("2469559669")
                .nickname(nickName)
                .type(Oauth.KAKAO)
                .build());
        //when
        OauthResponse oauthResponse = oauthService.oauthToken(request);
        //then
        assertEquals(nickName, oauthResponse.getNickname());
    }

    @Test
    @DisplayName("회원가입에 성공한다.")
    void oauthJoinSuccess(){
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
                        return "{\"id\":222222,\"connected_at\":\"2022-10-03T06:24:11Z\",\"properties\":{\"nickname\":\"최준호\",\"profile_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"thumbnail_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"최준호\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"is_default_image\":false},\"has_email\":true,\"email_needs_agreement\":false,\"is_email_valid\":true,\"is_email_verified\":true,\"email\":\"test@kakao.com\"}}";
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

        // when
        OauthResponse oauthResponse = oauthService.oauthJoin(request);

        // then
        assertEquals("test@kakao.com", oauthResponse.getEmail());
    }
}
