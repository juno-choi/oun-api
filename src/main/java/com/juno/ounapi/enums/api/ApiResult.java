package com.juno.ounapi.enums.api;

import lombok.Getter;

@Getter
public enum ApiResult {
    SUCCESS("0000", "", "success"),
    LOGIN_FAIL("0401", "ALERT", "아이디나 비밀번호를 확인해주세요."),
    BAD_REQUEST("0400", "잘못된 요청", "fail"),


    ;
    private String resultCode;
    private String resultType;
    private String resultMsg;

    ApiResult(String resultCode, String resultType, String resultMsg) {
        this.resultCode = resultCode;
        this.resultType = resultType;
        this.resultMsg = resultMsg;
    }

}
