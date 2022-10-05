package com.juno.ounapi.enums.api;

import lombok.Getter;

@Getter
public enum ApiResult {
    SUCCESS("0000", "", "success"),


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
