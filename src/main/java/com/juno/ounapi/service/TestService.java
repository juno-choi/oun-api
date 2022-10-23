package com.juno.ounapi.service;

import com.juno.ounapi.dto.TestDto;
import com.juno.ounapi.vo.TestResponse;

public interface TestService {
    TestResponse test(TestDto testDto);
}
