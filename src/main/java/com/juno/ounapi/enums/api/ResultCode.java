package com.juno.ounapi.enums.api;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResultCode {
    SUCCESS("0000"),
    INTERNAL_SERVER("9999"),
    FAIL("0500"),
    BAD_REQUEST("0400"),
    UN_AUTHORIZED("0401"),
    LOGIN_FAIL("0403"),
    ;
    public String code;

}
