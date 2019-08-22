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
		headers.set("AccessToken", "EAAj259ZBvk6wBAG7P7h3AMGQCgUzmfxw04g3oaN6Qq7gEM0Cjt8zXR050kfR6QZBf2KiIQ6FegaJGyLEfXtJ2srL774LcZB2ZCqJrpjJOZB64yZAIPBlPGWWMTJKGVdEqbyXWiPdNoZCSqzQG8wujCPd8vFWeCKoH03kyKx3f6yXIkoYHjsWVjcOZAZAUS6E79HNve8lE1osQdAZDZD");
		
		final HttpEntity<User> request = new HttpEntity<User>(headers);
		
		final ResponseEntity<User> user = restTemplate.postForEntity(uri, request, User.class);
		
		assertThat(user).isEqualTo(new User("2425138454230590", "Clayton Mendonça"));
	}
	
	@Test
	@Ignore
	public void testGettingUser() throws URISyntaxException {
		final URI uri = new URI(String.format("http://localhost:%d/v1/users/%s", port, "2425138454230590"));
		
		final ResponseEntity<User> user = restTemplate.getForEntity(uri, User.class);
		
		assertThat(user.getBody().getName()).isEqualTo("Clayton Mendonça");
	}

}
