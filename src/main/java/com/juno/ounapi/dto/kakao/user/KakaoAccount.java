package com.juno.ounapi.dto.kakao.user;

import lombok.*;

@Getter
@Setter
public class KakaoAccount {
    private String profile_nickname_needs_agreement;
    private String profile_image_needs_agreement;
    private Profile profile;
    private String has_email;
    private String email_needs_agreement;
    private String is_email_valid;
    private String is_email_verified;
    private String email;

    public KakaoAccount() {
    }
}
