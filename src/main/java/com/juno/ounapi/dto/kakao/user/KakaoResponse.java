package com.juno.ounapi.dto.kakao.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class KakaoResponse {
    private Long id;
    private LocalDateTime connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;
}
