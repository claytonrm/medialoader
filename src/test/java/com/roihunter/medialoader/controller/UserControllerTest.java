package com.roihunter.medialoader.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roihunter.medialoader.domain.User;
import com.roihunter.medialoader.service.UserService;
import com.roihunter.medialoader.util.Util;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	private static ObjectMapper mapper;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		mapper = new ObjectMapper();
	}
	
	@Test
	public void create_shouldCallServiceWithTokenToLoadData() throws Exception {
		final String accessToken = "EAAj259ZBvk6wBABiGRWYOvAGk7gMGwZDZD";
		final HttpHeaders headers = new HttpHeaders();
		headers.add("AccessToken", accessToken);

		final ArgumentCaptor<String> accessTokenCature = ArgumentCaptor.forClass(String.class);
		
		mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON).headers(headers))
			.andExpect(status().isCreated());
		
		verify(userService).create(accessTokenCature.capture());
	}
	
	@Test
	public void create_shouldReturnUserInfo() throws Exception {
		final String accessToken = "EAAj259ZBvk6wBABiGRWYOvAGk7gMGwZDZD";
		final User userInfoExpected = mapper.readValue(Util.readJsonFile("sampleUserResponse.json"), User.class);
		given(userService.create(accessToken)).willReturn(userInfoExpected);
		
		final HttpHeaders headers = new HttpHeaders();
		headers.add("AccessToken", accessToken);
		
		mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON).headers(headers))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is("1234567890")))
			.andExpect(jsonPath("$.name", is("Clayton R. Mendonca")));
	}
	
	@Test
	public void create_shouldReturnNotFound() throws Exception {
		final String accessToken = "EAAj259ZBvk6wBABiGRWYOvAGk7gMGwZDZD";
		final HttpHeaders headers = new HttpHeaders();
		headers.add("AccessToken", accessToken);
		
		mockMvc.perform(post("/v1/user").contentType(MediaType.APPLICATION_JSON).headers(headers))
		.andExpect(status().isNotFound());
	}
	
	
}
