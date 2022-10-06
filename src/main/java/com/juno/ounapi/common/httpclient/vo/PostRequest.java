package com.juno.ounapi.common.httpclient.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostRequest {
    private String uri;
    private String body;
    private String[] headers;
}
