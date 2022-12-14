package com.juno.ounapi.enums.api.oauth;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OauthFailMsg {
    KAKAO_LOGIN_FAIL("kakao로 인증된 정보가 없습니다. 회원가입을 먼저 진행해주세요!"),
    KAKAO_DUPLICATION_JOIN_FAIL("이미 가입된 회원입니다!"),
    ;
    public String message;
}
