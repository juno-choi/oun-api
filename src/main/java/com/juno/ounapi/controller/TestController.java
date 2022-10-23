package com.juno.ounapi.controller;

import com.juno.ounapi.common.ApiResponse;
import com.juno.ounapi.dto.TestDto;
import com.juno.ounapi.enums.api.ResultCode;
import com.juno.ounapi.enums.api.ResultType;
import com.juno.ounapi.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @PostMapping("/test")
    public ResponseEntity<ApiResponse<?>> test(@RequestBody TestDto dto){
        return ResponseEntity.ok(ApiResponse.builder()
                .resultCode(ResultCode.SUCCESS)
                .resultType(ResultType.NONE)
                .resultMsg("성공")
                .data(testService.test(dto))
                .build());
    }
}
