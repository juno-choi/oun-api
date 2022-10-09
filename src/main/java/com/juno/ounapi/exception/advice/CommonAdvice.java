package com.juno.ounapi.exception.advice;

import com.juno.ounapi.common.Error;
import com.juno.ounapi.common.ErrorResponse;
import com.juno.ounapi.enums.api.CommonExceptionCode;
import com.juno.ounapi.exception.CommonException;
import com.juno.ounapi.vo.kakao.EmptyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.juno.ounapi")
@Slf4j
public class CommonAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> commonException(CommonException e){
        CommonExceptionCode code = e.getCode();
        log.error(code.getResultCode(), e);

        if(code == CommonExceptionCode.LOGIN_FAIL){
            return ResponseEntity.status(code.getStatus())
                    .body(ErrorResponse.builder()
                            .resultCode(code.getResultCode())
                            .resultType(code.getResultType())
                            .resultMsg(code.getResultMsg())
                            .error(e.getError())
                            .build());
        }else{
            CommonExceptionCode undefinedCode = CommonExceptionCode.UNDEFINED_CODE;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .resultCode(undefinedCode.getResultCode())
                            .resultType(undefinedCode.getResultType())
                            .resultMsg(undefinedCode.getResultMsg())
                            .error(Error.builder().error(new EmptyResponse()).build())
                            .build());
        }
    }
}
