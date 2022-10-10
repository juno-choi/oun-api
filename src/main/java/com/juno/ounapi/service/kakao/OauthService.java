package com.juno.ounapi.service.kakao;

import com.juno.ounapi.dto.kakao.OauthRequest;
import com.juno.ounapi.vo.kakao.OauthResponse;

public interface OauthService {
    OauthResponse oauthToken(OauthRequest oauthRequest);
    OauthResponse oauthJoin(OauthRequest oauthRequest);
}
