package com.roihunter.medialoader.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roihunter.medialoader.client.GraphAPI;
import com.roihunter.medialoader.domain.Photo;
import com.roihunter.medialoader.domain.User;
import com.roihunter.medialoader.domain.facebook.FacebookUser;

@Service
public class UserService {
	
	@Autowired
	private GraphAPI graphApi;
	
	@Autowired
	private PhotoService photoService;
	
	
	public User create(final String token) {

		// {photo-id}/reactions?summary=total_count

		final String[] fields = new String[] { "gender", "picture", "name" };
		final FacebookUser facebookUser = graphApi.getProfileInfo(fields, token);
		final List<Photo> userPhotos = photoService.getUserPhotos(token);

		final User user = new User(facebookUser.getId(), 
				facebookUser.getName(), 
				facebookUser.getGender(), 
				userPhotos);

		return user;
	}

}
