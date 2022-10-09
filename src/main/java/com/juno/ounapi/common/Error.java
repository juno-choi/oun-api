package com.juno.ounapi.common;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Error<T> {
    private T error;
}
