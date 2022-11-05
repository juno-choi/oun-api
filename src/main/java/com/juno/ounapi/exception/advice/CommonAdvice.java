package com.juno.ounapi.exception.advice;

import com.juno.ounapi.common.Error;
import com.juno.ounapi.common.ErrorResponse;
import com.juno.ounapi.enums.api.ResultCode;
import com.juno.ounapi.enums.api.ResultType;
import com.juno.ounapi.exception.CommonException;
import com.juno.ounapi.vo.kakao.TempResponse;
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

        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.builder()
                        .resultCode(e.getResultCode())
                        .resultType(e.getResultType())
                        .resultMsg(e.getResultMsg())
                        .error(e.getError())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> runtimeException(RuntimeException e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .resultCode(ResultCode.INTERNAL_SERVER)
                        .resultType(ResultType.NONE)
                        .resultMsg("INTERNAL SERVER ERROR!")
                        .error(Error.builder().error(new TempResponse("서버 내부 에러가 발생했습니다. 관리자에게 문의해주세요.")).build())
                        .build());
    }
}
