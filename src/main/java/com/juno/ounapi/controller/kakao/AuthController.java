package com.juno.ounapi.controller.kakao;

import com.juno.ounapi.common.ApiResponse;
import com.juno.ounapi.dto.kakao.OauthRequest;
import com.juno.ounapi.enums.api.ResultCode;
import com.juno.ounapi.enums.api.ResultType;
import com.juno.ounapi.service.kakao.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/kakao")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final OauthService oauthService;

    @PostMapping("/auth")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody OauthRequest oauthRequest){
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .resultCode(ResultCode.SUCCESS)
                        .resultType(ResultType.NONE)
                        .resultMsg("정상")
                        .data(oauthService.oauthToken(oauthRequest))
                        .build()
        );
    }

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<?>> join(@RequestBody OauthRequest oauthRequest){
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .resultCode(ResultCode.SUCCESS)
                        .resultType(ResultType.NONE)
                        .resultMsg("정상")
                        .data(oauthService.oauthJoin(oauthRequest))
                        .build()
        );
    }
}
