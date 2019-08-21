package com.roihunter.medialoader.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityNotFoundException;

import org.apache.tomcat.websocket.AuthenticationException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
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
	
	private HttpHeaders headers;
	
	private String accessToken;
	
	private static ObjectMapper mapper;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		mapper = new ObjectMapper();
	}
	
	@Before
	public void setUp() {
		accessToken = "EAAj259ZBvk6wBABiGRWYOvAGk7gMGwZDZD";
		headers = new HttpHeaders();
		headers.add("AccessToken", "EAAj259ZBvk6wBABiGRWYOvAGk7gMGwZDZD");
	}
	
	@Test
	public void create_shouldCallServiceWithTokenToLoadData() throws Exception {
		final User userInfoExpected = mapper.readValue(Util.readJsonFile("sampleUserInfo.json"), User.class);
		given(userService.create(accessToken)).willReturn(userInfoExpected);
		
		final ArgumentCaptor<String> accessTokenCature = ArgumentCaptor.forClass(String.class);
		
		mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON).headers(headers))
			.andExpect(status().isCreated());
		
		verify(userService).create(accessTokenCature.capture());
	}

	@Test
	public void create_shouldReturnUnauthorizedTokenExpiredOrInvalid() throws Exception {
		BDDMockito.given(userService.create(accessToken)).willThrow(AuthenticationException.class);

		mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON).headers(headers))
			.andExpect(status().isUnauthorized());
		
		verify(userService).create(accessToken);
	}
	
	@Test
	public void create_shouldReturnUserInfo() throws Exception {
		final User userInfoExpected = mapper.readValue(Util.readJsonFile("sampleUserInfo.json"), User.class);
		given(userService.create(accessToken)).willReturn(userInfoExpected);
		
		mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON).headers(headers))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is("1234567890")))
			.andExpect(jsonPath("$.name", is("Clayton R. Mendonca")));
	}
	
	@Test
	public void create_shouldReturnNotFound() throws Exception {
		mockMvc.perform(post("/v1/user").contentType(MediaType.APPLICATION_JSON).headers(headers))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void delete_shouldCallServiceToDeleteRecordOnDatabase() throws Exception {
		mockMvc.perform(delete("/v1/users/{id}", "123456789").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
		
		verify(userService).delete("123456789");
	}
	
	@Test
	public void delete_shouldReturnNotFoundUserDoesNotExist() throws Exception {
		final String userId = "123456789";
		
		doThrow(EntityNotFoundException.class).when(userService).delete(userId);
		
		mockMvc.perform(delete("/v1/users/{id}", userId).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
		
		verify(userService).delete(userId);
	}
	
	@Test
	public void getUserInfo_shouldCallServiceToRetrieveUserInfo() throws Exception {
		final String userId = "1234567890";
		final User userInfoExpected = mapper.readValue(Util.readJsonFile("sampleUserInfo.json"), User.class);
		given(userService.get(userId)).willReturn(userInfoExpected);
		
		mockMvc.perform(get("/v1/users/{id}", userId).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is("1234567890")))
			.andExpect(jsonPath("$.name", is("Clayton R. Mendonca")))
			.andExpect(jsonPath("$.gender", is("male")))
			.andExpect(jsonPath("$.profilePicture", is("https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=anyway")));
		
		verify(userService).get(userId);
	}
	
	@Test
	public void getUserInfo_shouldReturnNotFoundUserDoesNotExist() throws Exception {
		final String userId = "1234567890";
		given(userService.get(userId)).willThrow(EntityNotFoundException.class);
		
		mockMvc.perform(get("/v1/users/{id}", userId).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
		
		verify(userService).get(userId);
	}
	
	@Test
	public void getUserPhotos_shouldCallServiceToRetrieveUserPhotos() throws Exception {
		final String userId = "1234567890";
		final User userInfoExpected = mapper.readValue(Util.readJsonFile("sampleUserPhotos.json"), User.class);
		given(userService.getPhotos(userId)).willReturn(userInfoExpected);
		
		mockMvc.perform(get("/v1/users/{id}/photos", userId).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").doesNotExist())
			.andExpect(jsonPath("$.name").doesNotExist())
			.andExpect(jsonPath("$.gender").doesNotExist())
			.andExpect(jsonPath("$.profilePicture").doesNotExist())
			.andExpect(jsonPath("$.photos[0].facebookUrl", is("https://www.facebook.com/SomeGroup/photos/a.23423/4343/?type=3")));
		
		verify(userService).getPhotos(userId);
	}
	
	@Test
	public void getUserPhotos_shouldReturnNotFoundUserDoesNotExist() throws Exception {
		final String userId = "1234567890";
		given(userService.getPhotos(userId)).willThrow(EntityNotFoundException.class);
		
		mockMvc.perform(get("/v1/users/{id}/photos", userId).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
		
		verify(userService).getPhotos(userId);
	}
	
}
