package com.juno.ounapi.enums.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonExceptionCode {
    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "0601", "ALERT", "아이디나 비밀번호를 확인해주세요."),
    UNDEFINED_CODE(HttpStatus.INTERNAL_SERVER_ERROR, "0900", "SERVER_ERROR", "정의되지 않은 에러가 발생했습니다."),
    ;
    private HttpStatus status;
    private String resultCode;
    private String resultType;
    private String resultMsg;

    CommonExceptionCode(HttpStatus status, String resultCode, String resultType, String resultMsg) {
        this.status = status;
        this.resultCode = resultCode;
        this.resultType = resultType;
        this.resultMsg = resultMsg;
    }
}
