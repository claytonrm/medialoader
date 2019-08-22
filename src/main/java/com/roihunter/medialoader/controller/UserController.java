package com.roihunter.medialoader.controller;

import java.net.URI;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.roihunter.medialoader.domain.User;
import com.roihunter.medialoader.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/users")
@Api(tags = "Users", value = "Resources to user management")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "Fetches data on Facebook Graph API and saves on database")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> create(@RequestHeader(name = "AccessToken", required = true) final String accessToken, 
			final UriComponentsBuilder uriBuilder) throws AuthenticationException {
		
		final User user = userService.create(accessToken);
	    final URI uri = uriBuilder.path("/v1/users/{id}").buildAndExpand(user.getFacebookId()).toUri();
		return ResponseEntity.created(uri).body(new User(user.getFacebookId(), user.getName()));
	}

	@ApiOperation(value = "Deletes an user (not on Facebook database) by Facebook ID")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable("id") final String id) {
		userService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@ApiOperation(value = "Gets user info by Facebook ID")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserInfo(@PathVariable("id") final String id) {
		final User user = userService.get(id);
		return ResponseEntity.ok(new User(user.getFacebookId(), user.getName(), 
				user.getGender(), user.getProfilePicture(), null));
	}

	@ApiOperation(value = "Get all user photos by Facebook ID")
	@GetMapping(value = "/{id}/photos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserPhotos(@PathVariable("id") final String id) {
		return ResponseEntity.ok(userService.getPhotos(id));
	}
	
	
}
