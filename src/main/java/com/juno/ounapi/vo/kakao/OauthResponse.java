package com.juno.ounapi.vo.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OauthResponse {
    private String nickname;
    private String access_token;
    private String access_token_expires;
    private String refresh_token;
    private String refresh_token_expires;
    private String profile_img;
    private String thumbnail_img;
    private String email;
}
