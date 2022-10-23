package com.juno.ounapi.controller;

import com.juno.ounapi.dto.TestDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureRestDocs(uriHost = "localhost", uriPort = 8080)
@AutoConfigureMockMvc
@Transactional(readOnly = true)
class TestControllerTest extends RestdocsTestSupport{

    @Test
    public void test() throws Exception {
        //given
        TestDto testDto = new TestDto(3L, "juno");

        //when
        ResultActions perform = mockMvc.perform(post("/v1/test/test")
                .content(convertString(testDto))
                .contentType(MediaType.APPLICATION_JSON));
        //then
        perform.andDo(docs.document(
                PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("id").type(JsonFieldType.NUMBER).description("member id"),
                        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("member name")
                ),
                PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("resultCode").type(JsonFieldType.STRING).description("result code"),
                        PayloadDocumentation.fieldWithPath("resultType").type(JsonFieldType.STRING).description("result type"),
                        PayloadDocumentation.fieldWithPath("resultMsg").type(JsonFieldType.STRING).description("result msg"),
                        PayloadDocumentation.fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("member id"),
                        PayloadDocumentation.fieldWithPath("data.name").type(JsonFieldType.STRING).description("member name")
                )
        ));
    }
}