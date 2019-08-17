package com.roihunter.medialoader.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
	private UserService service;
	
	@MockBean
	private GraphAPI graphApi;
	
	private static ObjectMapper mapper;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		mapper = new ObjectMapper();
	}
	
	@Test
	public void create_shouldCallFacebookClientService() throws JsonParseException, JsonMappingException, IOException {
		final String accessToken = "EAAj259ZBvk6wBABiGRWYOvAGk7gMGwZDZD";
		final User expectedUser = mapper.readValue(Util.readJsonFile("sampleUser.json"), User.class);
		final FacebookUser facebookUser = mapper.readValue(Util.readJsonFile("sampleFacebookUserData.json"), FacebookUser.class);
		given(graphApi.load(accessToken)).willReturn(facebookUser);
		
		final User user = service.create(accessToken);
		
		verify(graphApi).load(accessToken);
		assertThat(user).isEqualTo(expectedUser);
	}

}
