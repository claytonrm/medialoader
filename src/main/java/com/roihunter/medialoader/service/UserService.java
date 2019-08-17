package com.roihunter.medialoader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roihunter.medialoader.client.GraphAPI;
import com.roihunter.medialoader.domain.User;
import com.roihunter.medialoader.domain.facebook.FacebookUser;
import com.roihunter.medialoader.domain.facebook.ProfilePicture;

@Service
public class UserService {
	
	@Autowired
	private GraphAPI graphApi;
	
	public User create(final String token) {
		
		final String[] fields = new String[] {"gender", "picture", "name"};
		final FacebookUser facebookUser = graphApi.load(fields, token);
		final ProfilePicture profilePicture = facebookUser.getPicture();
		
		final User user = new User(facebookUser.getId(), 
				facebookUser.getName(), 
				facebookUser.getGender(), 
				profilePicture != null ? profilePicture.getData().getUrl() : null
			);
		
		return user;
	}

}
