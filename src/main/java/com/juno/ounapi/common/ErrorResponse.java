package com.juno.ounapi.common;

import com.juno.ounapi.enums.api.ResultCode;
import com.juno.ounapi.enums.api.ResultType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private String resultCode;
    private String resultType;
    private String resultMsg;
    private Error error;

    public ErrorResponse(ResultCode resultCode, ResultType resultType, String resultMsg, Error error) {
        this.resultCode = resultCode.name();
        this.resultType = resultType.name();
        this.resultMsg = resultMsg;
        this.error = error;
    }

    @Builder
    public ErrorResponse(String resultCode, String resultType, String resultMsg, Error error) {
        this.resultCode = resultCode;
        this.resultType = resultType;
        this.resultMsg = resultMsg;
        this.error = error;
    }
}
