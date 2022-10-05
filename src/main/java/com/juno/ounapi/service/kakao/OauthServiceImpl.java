package com.juno.ounapi.service.kakao;

import com.juno.ounapi.dto.kakao.OauthRequest;
import com.juno.ounapi.vo.kakao.OauthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OauthServiceImpl implements OauthService{
    @Override
    public OauthResponse oauthToken(OauthRequest oauthRequest) {
        log.debug("access token = {}", oauthRequest.getAccess_token());
        return OauthResponse.builder()
                .name("test")
                .build();
    }
}
