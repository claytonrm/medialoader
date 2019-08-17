package com.roihunter.medialoader.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roihunter.medialoader.client.GraphAPI;
import com.roihunter.medialoader.domain.Photo;
import com.roihunter.medialoader.domain.facebook.FacebookData;

@Service
public class PhotoService {
	
	@Autowired
	private GraphAPI graphApi;
	
	public List<Photo> getUserPhotos(final String accessToken) {
		final String[] fields = new String[] {"id, link, album, images, from"};
		
		final FacebookData data = graphApi.getUserPhotos(fields, accessToken);
		
		if (data == null) {
			return Collections.emptyList();
		}
		
		final List<Photo> userPhotos = new ArrayList<Photo>();
		data.getData().stream().forEach(photo -> {
			final Photo newPhoto = new Photo(); 
			newPhoto.setId(photo.getId());
			newPhoto.setFacebookUrl(photo.getLink());
			newPhoto.setSourceUrl(photo.getImages().stream().findFirst().get().getSource());
			userPhotos.add(newPhoto);
		});
		
		
		return userPhotos;
		
	}
	
	
	
	
}
