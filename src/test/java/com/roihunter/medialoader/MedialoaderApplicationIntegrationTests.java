package com.roihunter.medialoader;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.roihunter.medialoader.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MedialoaderApplicationIntegrationTests {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	@Ignore("Needs to refresh token")
	public void testCreatingUser() throws URISyntaxException {
		final URI uri = new URI(String.format("http://localhost:%d/v1/users", port));
		
		final HttpHeaders headers = new HttpHeaders();
		headers.set("AccessToken", "EAAj259ZBvk6wBAC386sUgqlkKmWh7WrgTTZCbLJGEcjWuZCNcb2spppNXyHax0OZCq6zU4m7ehEgRyum3ysKGDf08Ihm8WP1dG62sJniRWA9wBJm6m0bqmFQsE87U4xbJmcYQMMhzJLcXXwqEIfHbZAtjiVFk5AfwAQ5G83JjjKoAZCqdNLsSmKCdwdUVleWDbosckwbvuwwZDZD");
		
		final HttpEntity<User> request = new HttpEntity<User>(headers);
		
		final ResponseEntity<User> user = restTemplate.postForEntity(uri, request, User.class);
		
		assertThat(user).isEqualTo(new User("2425138454230590", "Clayton Mendonça"));
	}

}
