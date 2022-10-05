package com.juno.ounapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.juno.ounapi.dto.kakao.user.KakaoResponse;
import com.juno.ounapi.dto.kakao.user.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class OunApiApplicationTests {

	@Test
	void test() throws JsonProcessingException {
		String json = "{\"nickname\":\"tester\", \"thumbnail_image_url\":\"testurl\", \"profile_image_url\":\"testurl2\", \"is_default_image\":true}";
		ObjectMapper objectMapper = new ObjectMapper();
		Profile profile = objectMapper.readValue(json, Profile.class);
		System.out.println(profile.getNickname());
	}

	@Test
	void test2() throws JsonProcessingException {
		String json = "{\"id\":2469559669,\"connected_at\":\"2022-10-03T06:24:11Z\",\"properties\":{\"nickname\":\"최준호\",\"profile_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"thumbnail_image\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"최준호\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/bHdRC1/btrNQSBajfI/cFJiaU9WnODens10jJXdOK/img_640x640.jpg\",\"is_default_image\":false},\"has_email\":true,\"email_needs_agreement\":false,\"is_email_valid\":true,\"is_email_verified\":true,\"email\":\"ililil9482@naver.com\"}}";
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		KakaoResponse kakaoResponse = objectMapper.readValue(json, KakaoResponse.class);
		System.out.println(kakaoResponse.getId());
	}

}
