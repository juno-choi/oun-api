package com.juno.ounapi.service.kakao;

import com.juno.ounapi.common.httpclient.MyHttpClient;
import com.juno.ounapi.domain.member.Member;
import com.juno.ounapi.dto.kakao.OauthRequest;
import com.juno.ounapi.enums.oauth.Oauth;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
public class OauthServiceImplTest {
    @Autowired
    OauthService oauthService;

    @MockBean
    MyHttpClient myHttpClient;

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
        Member member = new Member(1L, Oauth.KAKAO.name(), "123456", "ililil9482@naver.com", null, "최준호", null, null, "profile image", "thumbnail image", LocalDateTime.now(), LocalDateTime.now());

        // when
        OauthResponse oauthResponse = oauthService.oauthJoin(request);

        // then
        assertEquals(member.getEmail(), oauthResponse.getEmail());
    }
}
