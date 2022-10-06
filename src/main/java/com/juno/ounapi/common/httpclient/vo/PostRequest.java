package com.juno.ounapi.common.httpclient.vo;

import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Builder
public class PostRequest {
    private String uri;
    private String body;
    private String[] headers;
}
