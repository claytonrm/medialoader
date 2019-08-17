package com.roihunter.medialoader.service;

import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roihunter.medialoader.client.GraphAPI;
import com.roihunter.medialoader.domain.Photo;
import com.roihunter.medialoader.domain.User;
import com.roihunter.medialoader.domain.facebook.FacebookData;
import com.roihunter.medialoader.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhotoServiceTest {
	
	@Autowired
	@InjectMocks
	private PhotoService service;
	
	@Mock
	private GraphAPI graphApi;
	
	private static ObjectMapper mapper;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		mapper = new ObjectMapper();
	}
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getUserPhotos_shouldReturnAListOfUserPhotos() throws JsonParseException, JsonMappingException, IOException {
		final String accessToken = "EAAj259ZBvk6wBABiGRWYOvAGk7gMGwZDZD";
		final FacebookData expectedData = mapper.readValue(Util.readJsonFile("sampleFacebookUserPhotos.json"), FacebookData.class);
		final User expectedUser = mapper.readValue(Util.readJsonFile("sampleUserWithoutReactions.json"), User.class);
		given(graphApi.getUserPhotos(new String[] {"id, link, album, images, from"}, accessToken)).willReturn(expectedData);
		
		final List<Photo> userPhotos = service.getUserPhotos(accessToken);
		
		Assertions.assertThat(userPhotos).isEqualTo(expectedUser.getPhotos());
	}
	
	

}
