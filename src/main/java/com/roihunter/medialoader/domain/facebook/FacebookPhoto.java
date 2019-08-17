package com.roihunter.medialoader.domain.facebook;

import java.util.List;

import lombok.Data;

@Data
public class FacebookPhoto {
	
	private String id;
	private String link;
	private FacebookAlbum album;
	private List<PictureDetail> images;
	private PostFrom from;
	
}
