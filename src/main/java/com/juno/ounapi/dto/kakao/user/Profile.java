package com.juno.ounapi.dto.kakao.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private String nickname;
    private String thumbnail_image_url;
    private String profile_image_url;
    private String is_default_image;
}
