package com.juno.ounapi.common;

import com.juno.ounapi.enums.api.ResultCode;
import com.juno.ounapi.enums.api.ResultType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse<T>{
    private String resultCode;
    private String resultType;
    private String resultMsg;
    private T error;

    @Builder
    public ErrorResponse(ResultCode resultCode, ResultType resultType, String resultMsg, T error) {
        this.resultCode = resultCode.code;
        this.resultType = resultType.name();
        this.resultMsg = resultMsg;
        this.error = error;
    }
}
