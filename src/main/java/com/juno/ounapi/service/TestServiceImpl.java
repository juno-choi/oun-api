package com.juno.ounapi.service;

import com.juno.ounapi.dto.TestDto;
import com.juno.ounapi.vo.TestResponse;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService{
    @Override
    public TestResponse test(TestDto testDto) {

        Long id = testDto.getId()+1;
        String name = "test = "+testDto.getName();

        return TestResponse.builder()
                .id(id)
                .name(name)
                .build();
    }
}
