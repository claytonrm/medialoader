package com.roihunter.medialoader.controller;

import java.net.URI;

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

@RestController
@RequestMapping("/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> create(@RequestHeader(name = "AccessToken", required = true) final String accessToken, 
			final UriComponentsBuilder uriBuilder) {
		
		final User user = userService.create(accessToken);
	    final URI uri = uriBuilder.path("/v1/users/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(new User(user.getId(), user.getName()));
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable("id") final String id) {
		userService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserInfo(@PathVariable("id") final String id) {
		return ResponseEntity.ok(userService.get(id));
	}

	@GetMapping(value = "/{id}/photos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserPhotos(@PathVariable("id") final String id) {
		return ResponseEntity.ok(userService.getPhotos(id));
	}
	
	
}
