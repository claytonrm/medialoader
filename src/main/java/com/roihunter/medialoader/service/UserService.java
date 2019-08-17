package com.roihunter.medialoader.service;

import org.springframework.stereotype.Service;

import com.roihunter.medialoader.client.GraphAPI;
import com.roihunter.medialoader.domain.User;
import com.roihunter.medialoader.domain.facebook.FacebookUser;

@Service
public class UserService {
	
	private GraphAPI graphApi;
	
	public User create(final String token) {
		
		final FacebookUser facebookUser = graphApi.load(token);
		
		final User user = new User(facebookUser.getId(), facebookUser.getName(), facebookUser
				.getGender(), facebookUser.getPicture().getData().getUrl());
		
		return user;
	}

}
