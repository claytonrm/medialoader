package com.roihunter.medialoader.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roihunter.medialoader.client.GraphAPI;
import com.roihunter.medialoader.domain.User;
import com.roihunter.medialoader.domain.facebook.FacebookUser;
import com.roihunter.medialoader.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	@InjectMocks
	private UserService service;
	
	@Mock
	private GraphAPI graphApi;
	
	@Mock
	private PhotoService photoService;
	
	private static ObjectMapper mapper;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		mapper = new ObjectMapper();
	}
	
	@Before
	public void setUp() {
		initMocks(this);
	}
	
	@Test
	public void create_shouldCallFacebookClientServiceToGetUserInfo() throws JsonParseException, JsonMappingException, IOException {
		final String accessToken = "EAAj259ZBvk6wBABiGRWYOvAGk7gMGwZDZD";
		final User expectedUser = mapper.readValue(Util.readJsonFile("sampleUser.json"), User.class);
		final FacebookUser facebookUser = mapper.readValue(Util.readJsonFile("sampleFacebookUserData.json"), FacebookUser.class);
		given(graphApi.getProfileInfo(new String[] {"gender", "picture", "name"}, accessToken)).willReturn(facebookUser);
		
		final User user = service.create(accessToken);
		
		verify(graphApi).getProfileInfo(new String[] {"gender", "picture", "name"}, accessToken);
		assertThat(user.getFacebookId()).isEqualTo(expectedUser.getFacebookId());
		assertThat(user.getName()).isEqualTo(expectedUser.getName());
		assertThat(user.getGender()).isEqualTo(expectedUser.getGender());
	}
	
	@Test
	public void create_shouldFillUserInfoWithPhotosCallingPhotoService() throws JsonParseException, JsonMappingException, IOException {
		final String accessToken = "EAAj259ZBvk6wBABiGRWYOvAGk7gMGwZDZD";
		final User expectedUser = mapper.readValue(Util.readJsonFile("sampleUserWithoutReactions.json"), User.class);
		final FacebookUser facebookUser = mapper.readValue(Util.readJsonFile("sampleFacebookUserData.json"), FacebookUser.class);
		given(graphApi.getProfileInfo(new String[] {"gender", "picture", "name"}, accessToken)).willReturn(facebookUser);
		given(photoService.getUserPhotos(accessToken)).willReturn(expectedUser.getPhotos());
		
		final User user = service.create(accessToken);
		
		verify(photoService).getUserPhotos(accessToken);
		
		assertThat(user).isEqualTo(expectedUser);
	}

}
