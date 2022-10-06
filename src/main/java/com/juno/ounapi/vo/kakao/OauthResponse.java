package com.juno.ounapi.vo.kakao;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OauthResponse {
    private String nickname;
    private String access_token;
    private String access_token_expires;
    private String profile_img;
}
