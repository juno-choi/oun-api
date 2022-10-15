package com.juno.ounapi.common;

import com.juno.ounapi.enums.api.ResultCode;
import com.juno.ounapi.enums.api.ResultType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    private String resultCode;
    private String resultType;
    private String resultMsg;
    private T data;

    @Builder
    public ApiResponse(ResultCode resultCode, ResultType resultType, String resultMsg, T data) {
        this.resultCode = resultCode.name();
        this.resultType = resultType.name();
        this.resultMsg = resultMsg;
        this.data = data;
    }
}
