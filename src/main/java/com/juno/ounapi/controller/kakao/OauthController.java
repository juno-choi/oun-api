package com.juno.ounapi.controller.kakao;

import com.juno.ounapi.common.ApiResponse;
import com.juno.ounapi.dto.kakao.OauthRequest;
import com.juno.ounapi.enums.api.ApiResult;
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
public class OauthController {
    private final OauthService oauthService;

    @PostMapping("/oauth")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody OauthRequest oauthRequest){
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .apiResult(ApiResult.SUCCESS)
                        .data(oauthService.oauthToken(oauthRequest))
                        .build()
        );
    }

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<?>> join(@RequestBody OauthRequest oauthRequest){
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .apiResult(ApiResult.SUCCESS)
                        .data(oauthService.oauthJoin(oauthRequest))
                        .build()
        );
    }
}
