package com.juno.ounapi.common;

import com.juno.ounapi.enums.api.ApiResult;
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
    public ApiResponse(ApiResult apiResult, T data) {
        this.resultCode = apiResult.getResultCode();
        this.resultType = apiResult.getResultType();
        this.resultMsg = apiResult.getResultMsg();
        this.data = data;
    }
}
