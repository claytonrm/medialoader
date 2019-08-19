package com.roihunter.medialoader.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roihunter.medialoader.client.GraphAPI;
import com.roihunter.medialoader.domain.Photo;
import com.roihunter.medialoader.domain.User;
import com.roihunter.medialoader.domain.facebook.FacebookUser;

@Service
public class UserService implements MediaLoaderService<User> {
	
	@Autowired
	private GraphAPI graphApi;
	
	@Autowired
	private PhotoService photoService;
	
	@Override
	public User create(final String token) {
		final String[] fields = new String[] { "gender", "picture", "name" };
		final FacebookUser facebookUser = graphApi.getProfileInfo(fields, token);
		final List<Photo> userPhotos = photoService.getUserPhotos(token);

		final String profilePicureUrl = facebookUser.getPicture() != null ? facebookUser.getPicture().getData().getUrl() : null;
		
		final User user = new User(facebookUser.getId(), 
				facebookUser.getName(), 
				facebookUser.getGender(), 
				profilePicureUrl,
				userPhotos);
		
		//Call repository

		return user;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		//Call repository
		
	}
	
	@Override
	public User get(String id) {
		// TODO Auto-generated method stub
		//Call repository
		return null;
	}

	
	public User getPhotos(final String id) {
		//Call repository
		return null;
		
	}

}
