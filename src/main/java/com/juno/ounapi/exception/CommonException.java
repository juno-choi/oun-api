package com.juno.ounapi.exception;

import com.juno.ounapi.common.Error;
import com.juno.ounapi.enums.api.CommonExceptionCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{
    private CommonExceptionCode code;
    private Error error;

    public CommonException(CommonExceptionCode code, Error error) {
        super(code.name());
        this.code = code;
        this.error = error;
    }

}
