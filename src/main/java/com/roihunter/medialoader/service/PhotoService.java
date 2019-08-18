package com.roihunter.medialoader.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roihunter.medialoader.client.GraphAPI;
import com.roihunter.medialoader.domain.Photo;
import com.roihunter.medialoader.domain.Reaction;
import com.roihunter.medialoader.domain.ReactionType;
import com.roihunter.medialoader.domain.facebook.FacebookData;
import com.roihunter.medialoader.domain.facebook.ReactionsSummary;

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
		return fillUserPhotos(data, accessToken);
	}

	private List<Photo> fillUserPhotos(final FacebookData data, final String accessToken) {
		final List<Photo> userPhotos = new ArrayList<Photo>();
		
		data.getData().stream().forEach(photo -> {
			final Photo newPhoto = new Photo(); 
			newPhoto.setId(photo.getId());
			newPhoto.setFacebookUrl(photo.getLink());
			newPhoto.setSourceUrl(photo.getImages().stream().findFirst().get().getSource());
			newPhoto.setReactions(getReactions(accessToken));
			userPhotos.add(newPhoto);
		});
		return userPhotos;
	}
	
	private List<Reaction> getReactions(final String accessToken) {
		final String[] fields = new String[ReactionType.values().length];
		for (int i = 0; i < ReactionType.values().length; i++) {
			fields[i] = ReactionType.values()[i].getQuery();
		}
		
		final ReactionsSummary summary = graphApi.getReactions(fields, accessToken);
		
		if (summary == null) {
			return Collections.emptyList();
		}
		
		return Arrays.asList(
				new Reaction(ReactionType.LIKE, summary.getLike().getSummary().getTotalCount()),
				new Reaction(ReactionType.LOVE, summary.getLove().getSummary().getTotalCount()),
				new Reaction(ReactionType.WOW, summary.getWow().getSummary().getTotalCount()),
				new Reaction(ReactionType.HAHA, summary.getHahaha().getSummary().getTotalCount()),
				new Reaction(ReactionType.SAD, summary.getSad().getSummary().getTotalCount()),
				new Reaction(ReactionType.ANGRY, summary.getAngry().getSummary().getTotalCount())
			);
	}
	
	
	
	
}
