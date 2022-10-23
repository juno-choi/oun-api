package com.juno.ounapi.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class TestResponse {
    private Long id;
    private String name;
}
