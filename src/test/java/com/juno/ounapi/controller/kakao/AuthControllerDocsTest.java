package com.juno.ounapi.controller.kakao;

import com.juno.ounapi.common.httpclient.MyHttpClient;
import com.juno.ounapi.config.RestdocsTestSupport;
import com.juno.ounapi.domain.member.Member;
import com.juno.ounapi.dto.kakao.OauthRequest;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.*;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Execution(ExecutionMode.SAME_THREAD)
class AuthControllerDocsTest extends RestdocsTestSupport {

    @MockBean
    private MyHttpClient myHttpClient;

    @Autowired
    private MemberRepository memberRepository;

    private final String PREFIX_URL = "/v1/kakao";

    @Test
    @DisplayName("/v1/kakao/join")
    void join() throws Exception {
        // given
        OauthRequest request = new OauthRequest("kakao-access-token", "bearer", "kakao-refresh-token", 21599L, "account_email profile_image profile_nickname", 5183999L);
        given(myHttpClient.httpPostRequest(any())).willReturn(new HttpResponse<>() {
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
                return "{\"id\":1234567890,\"connected_at\":\"2022-10-03T06:24:11Z\",\"properties\":{\"nickname\":\"?????????\",\"profile_image\":\"http://k.kakaocdn.net/dn/c9KUpM/btrPfNZe4AA/tA7XfkrHGjxRW9RAa6bCs0/img_640x640.jpg\",\"thumbnail_image\":\"http://k.kakaocdn.net/dn/c9KUpM/btrPfNZe4AA/tA7XfkrHGjxRW9RAa6bCs0/img_110x110.jpg\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"?????????\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/c9KUpM/btrPfNZe4AA/tA7XfkrHGjxRW9RAa6bCs0/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/c9KUpM/btrPfNZe4AA/tA7XfkrHGjxRW9RAa6bCs0/img_640x640.jpg\",\"is_default_image\":false},\"has_email\":true,\"email_needs_agreement\":false,\"is_email_valid\":true,\"is_email_verified\":true,\"email\":\"juno@mail.com\"}}";
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
                return null;
            }
        });
        // when
        ResultActions perform = mockMvc.perform(post(PREFIX_URL+"/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToString(request)));
        // then
        perform.andDo(docs.document(
                requestFields(
                        fieldWithPath("access_token").type(JsonFieldType.STRING).description("kakao access token"),
                        fieldWithPath("token_type").type(JsonFieldType.STRING).description("kakao token type"),
                        fieldWithPath("refresh_token").type(JsonFieldType.STRING).description("kakao refresh token"),
                        fieldWithPath("expires_in").type(JsonFieldType.NUMBER).description("kakao access token ?????????"),
                        fieldWithPath("scope").type(JsonFieldType.STRING).description("kakao ?????? ??????"),
                        fieldWithPath("refresh_token_expires_in").type(JsonFieldType.NUMBER).description("kakao refresh token ?????????")
                ),
                responseFields(
                        fieldWithPath("resultCode").type(JsonFieldType.STRING).description("result code"),
                        fieldWithPath("resultType").type(JsonFieldType.STRING).description("result type"),
                        fieldWithPath("resultMsg").type(JsonFieldType.STRING).description("result msg"),
                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data.access_token").type(JsonFieldType.STRING).description("oun access token"),
                        fieldWithPath("data.access_token_expires").type(JsonFieldType.STRING).description("oun access token ?????????"),
                        fieldWithPath("data.refresh_token").type(JsonFieldType.STRING).description("oun refresh token"),
                        fieldWithPath("data.refresh_token_expires").type(JsonFieldType.STRING).description("oun refresh token ?????????"),
                        fieldWithPath("data.profile_img").type(JsonFieldType.STRING).description("?????? ????????? ?????????"),
                        fieldWithPath("data.thumbnail_img").type(JsonFieldType.STRING).description("?????? ????????? ?????????"),
                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("?????? ?????????")
                )
        ));
    }

    @Test
    @DisplayName("/v1/kakao/auth")
    public void auth() throws Exception {
        //given
        OauthRequest request = new OauthRequest("kakao-access-token", "bearer", "kakao-refresh-token", 21599L, "account_email profile_image profile_nickname", 5183999L);
        given(myHttpClient.httpPostRequest(any())).willReturn(new HttpResponse<>() {
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
                return "{\"id\":1234,\"connected_at\":\"2022-10-03T06:24:11Z\",\"properties\":{\"nickname\":\"tester\",\"profile_image\":\"http://k.kakaocdn.net/dn/c9KUpM/btrPfNZe4AA/tA7XfkrHGjxRW9RAa6bCs0/img_640x640.jpg\",\"thumbnail_image\":\"http://k.kakaocdn.net/dn/c9KUpM/btrPfNZe4AA/tA7XfkrHGjxRW9RAa6bCs0/img_110x110.jpg\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"?????????\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/c9KUpM/btrPfNZe4AA/tA7XfkrHGjxRW9RAa6bCs0/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/c9KUpM/btrPfNZe4AA/tA7XfkrHGjxRW9RAa6bCs0/img_640x640.jpg\",\"is_default_image\":false},\"has_email\":true,\"email_needs_agreement\":false,\"is_email_valid\":true,\"is_email_verified\":true,\"email\":\"juno@mail.com\"}}";
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
                return null;
            }
        });

        memberRepository.save(Member.builder()
                .memberId("1234")
                .nickname("tester")
                .type(Oauth.KAKAO)
                .profile("profile-image")
                .thumbnail("thumbnail-image")
                .email("tester@email.com")
                .build());

        //when
        ResultActions perform = mockMvc.perform(
                post(PREFIX_URL + "/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToString(request)));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(docs.document(
                requestFields(
                        fieldWithPath("access_token").type(JsonFieldType.STRING).description("kakao access token"),
                        fieldWithPath("token_type").type(JsonFieldType.STRING).description("kakao token type"),
                        fieldWithPath("refresh_token").type(JsonFieldType.STRING).description("kakao refresh token"),
                        fieldWithPath("expires_in").type(JsonFieldType.NUMBER).description("kakao access token ?????????"),
                        fieldWithPath("scope").type(JsonFieldType.STRING).description("kakao ?????? ??????"),
                        fieldWithPath("refresh_token_expires_in").type(JsonFieldType.NUMBER).description("kakao refresh token ?????????")
                ),
                responseFields(
                        fieldWithPath("resultCode").type(JsonFieldType.STRING).description("result code"),
                        fieldWithPath("resultType").type(JsonFieldType.STRING).description("result type"),
                        fieldWithPath("resultMsg").type(JsonFieldType.STRING).description("result msg"),
                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data.access_token").type(JsonFieldType.STRING).description("oun access token"),
                        fieldWithPath("data.access_token_expires").type(JsonFieldType.STRING).description("oun access token ?????????"),
                        fieldWithPath("data.refresh_token").type(JsonFieldType.STRING).description("oun refresh token"),
                        fieldWithPath("data.refresh_token_expires").type(JsonFieldType.STRING).description("oun refresh token ?????????"),
                        fieldWithPath("data.profile_img").type(JsonFieldType.STRING).description("?????? ????????? ?????????"),
                        fieldWithPath("data.thumbnail_img").type(JsonFieldType.STRING).description("?????? ????????? ?????????"),
                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("?????? ?????????")
                )
        ));
    }
}