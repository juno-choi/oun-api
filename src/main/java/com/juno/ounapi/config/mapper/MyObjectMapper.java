package com.juno.ounapi.config.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

@Component
public class MyObjectMapper extends ObjectMapper{
    public MyObjectMapper() {
        this.registerModule(new JavaTimeModule());
    }
}
