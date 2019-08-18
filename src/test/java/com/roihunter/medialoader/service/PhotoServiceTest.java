package com.roihunter.medialoader.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.List;

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
import com.roihunter.medialoader.domain.ReactionType;
import com.roihunter.medialoader.domain.User;
import com.roihunter.medialoader.domain.facebook.FacebookData;
import com.roihunter.medialoader.domain.facebook.ReactionsSummary;
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
		final String[] fields = new String[ReactionType.values().length];
		for (int i = 0; i < ReactionType.values().length; i++) {
			fields[i] = ReactionType.values()[i].getQuery();
		}
		given(graphApi.getReactions(fields, accessToken)).willReturn(null);
		
		final List<Photo> userPhotos = service.getUserPhotos(accessToken);
		
		assertThat(userPhotos).isEqualTo(expectedUser.getPhotos());
	}
	
	@Test
	public void getUserPhotos_shouldFillObjectWithReactions() throws JsonParseException, JsonMappingException, IOException {
		final String accessToken = "EAAj259ZBvk6wBABiGRWYOvAGk7gMGwZDZD";
		final FacebookData expectedData = mapper.readValue(Util.readJsonFile("sampleFacebookUserPhotos.json"), FacebookData.class);
		final User expectedUser = mapper.readValue(Util.readJsonFile("sampleUser.json"), User.class);
		given(graphApi.getUserPhotos(new String[] {"id, link, album, images, from"}, accessToken)).willReturn(expectedData);
		
		final ReactionsSummary reactionsSummary = mapper.readValue(Util.readJsonFile("sampleFacebookReactions.json"), ReactionsSummary.class);
		
		final String[] fields = new String[ReactionType.values().length];
		for (int i = 0; i < ReactionType.values().length; i++) {
			fields[i] = ReactionType.values()[i].getQuery();
		}
		
		given(graphApi.getReactions(fields, accessToken)).willReturn(reactionsSummary);
		
		final List<Photo> userPhotos = service.getUserPhotos(accessToken);
		
		assertThat(userPhotos.stream().findFirst().get().getReactions()).isEqualTo(expectedUser.getPhotos().stream().findFirst().get().getReactions());
		
	}
	
	

}
