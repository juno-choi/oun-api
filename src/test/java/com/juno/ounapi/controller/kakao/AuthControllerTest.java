package com.juno.ounapi.controller.kakao;

import com.juno.ounapi.common.httpclient.MyHttpClient;
import com.juno.ounapi.config.RestdocsTestSupport;
import com.juno.ounapi.domain.member.Member;
import com.juno.ounapi.dto.kakao.OauthRequest;
import com.juno.ounapi.enums.api.oauth.OauthFailMsg;
import com.juno.ounapi.enums.oauth.Oauth;
import com.juno.ounapi.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Execution(ExecutionMode.SAME_THREAD)
public class AuthControllerTest extends RestdocsTestSupport {
    @MockBean
    private MyHttpClient myHttpClient;

    @Autowired
    private MemberRepository memberRepository;

    private final String PREFIX_URL = "/v1/kakao";

    @Test
    @DisplayName("회원가입 하지 않은 회원은 로그인에 실패한다.")
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
                        return "{\"id\":33333,\"connected_at\":\"2022-10-03T06:24:11Z\",\"properties\":{\"nickname\":\"최준호\",\"profile_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"thumbnail_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"최준호\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"is_default_image\":false},\"has_email\":true,\"email_needs_agreement\":false,\"is_email_valid\":true,\"is_email_verified\":true,\"email\":\"ililil9482@naver.com\"}}";
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
        ResultActions perform = mockMvc.perform(
                post(PREFIX_URL+ "/auth").contentType(MediaType.APPLICATION_JSON)
                        .content(convertToString(request))
        );

        //then
        perform.andExpect(status().is4xxClientError());
        perform.andExpect(result ->
                assertTrue(result.getResponse()
                        .getContentAsString(StandardCharsets.UTF_8)
                        .contains(OauthFailMsg.KAKAO_LOGIN_FAIL.message))
        );
    }

    @Test
    @DisplayName("중복된 회원은 가입에 실패한다.")
    public void joinFail1() throws Exception {
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
                        return "{\"id\":44444,\"connected_at\":\"2022-10-03T06:24:11Z\",\"properties\":{\"nickname\":\"최준호\",\"profile_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"thumbnail_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"최준호\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"is_default_image\":false},\"has_email\":true,\"email_needs_agreement\":false,\"is_email_valid\":true,\"is_email_verified\":true,\"email\":\"ililil9482@naver.com\"}}";
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
        memberRepository.save(Member.builder()
                .memberId("44444")
                .type(Oauth.KAKAO)
                .build());
        //when
        ResultActions perform = mockMvc.perform(
                post(PREFIX_URL+"/join").contentType(MediaType.APPLICATION_JSON)
                        .content(convertToString(request))
        );
        //then
        perform.andReturn().getResponse()
                .getContentAsString(StandardCharsets.UTF_8)
                .contains(OauthFailMsg.KAKAO_DUPLICATION_JOIN_FAIL.message);
    }
}
