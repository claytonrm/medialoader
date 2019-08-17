package com.roihunter.medialoader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roihunter.medialoader.client.GraphAPI;
import com.roihunter.medialoader.domain.User;
import com.roihunter.medialoader.domain.facebook.FacebookUser;

@Service
public class UserService {
	
	@Autowired
	private GraphAPI graphApi;
	
	public User create(final String token) {
		
		//fields=id, link, album, images, from
		//{photo-id}/reactions?summary=total_count
		
		final String[] fields = new String[] {"gender", "picture", "name"};
		final FacebookUser facebookUser = graphApi.getProfileInfo(fields, token);
		
		final User user = new User(
				facebookUser.getId(), 
				facebookUser.getName(), 
				facebookUser.getGender(),
				null
			);
		

		return user;
	}

}
