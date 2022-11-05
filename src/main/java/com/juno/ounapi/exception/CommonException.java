package com.juno.ounapi.exception;

import com.juno.ounapi.common.Error;
import com.juno.ounapi.enums.api.ResultCode;
import com.juno.ounapi.enums.api.ResultType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonException extends RuntimeException{
    private HttpStatus httpStatus;
    private ResultCode resultCode;
    private ResultType resultType;
    private String resultMsg;
    private Error error;

    public CommonException(HttpStatus httpStatus, ResultCode resultCode, ResultType resultType, String resultMsg, Error error) {
        super(String.format("code:%s message:%s", resultCode, resultMsg));
        this.httpStatus = httpStatus;
        this.resultCode = resultCode;
        this.resultType = resultType;
        this.resultMsg = resultMsg;
        this.error = error;
    }
}
