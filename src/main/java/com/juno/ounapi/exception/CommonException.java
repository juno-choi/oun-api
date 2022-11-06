package com.juno.ounapi.exception;

import com.juno.ounapi.common.error.Error;
import com.juno.ounapi.common.error.TempResponse;
import com.juno.ounapi.enums.api.ResultCode;
import com.juno.ounapi.enums.api.ResultType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class CommonException extends RuntimeException{
    private HttpStatus httpStatus;
    private ResultCode resultCode;
    private ResultType resultType;
    private String resultMsg;
    private Error error;

    public CommonException(HttpStatus httpStatus, ResultCode resultCode, ResultType resultType, String resultMsg) {
        super(String.format("CODE: %s MESSAGE: %s", resultCode.code, resultMsg));
        this.httpStatus = httpStatus;
        this.resultCode = resultCode;
        this.resultType = resultType;
        this.resultMsg = resultMsg;
        this.error = new Error();
    }

    public CommonException(HttpStatus httpStatus, ResultCode resultCode, ResultType resultType, String resultMsg, Error error) {
        super(String.format("CODE: %s MESSAGE: %s", resultCode.code, resultMsg));
        this.httpStatus = httpStatus;
        this.resultCode = resultCode;
        this.resultType = resultType;
        this.resultMsg = resultMsg;
        this.error = error;
    }
}
