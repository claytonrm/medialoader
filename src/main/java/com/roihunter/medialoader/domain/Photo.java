package com.roihunter.medialoader.domain;

import java.util.List;

import lombok.Data;

@Data
public class Photo {

	private String id;
	private String facebookUrl;
	private String sourceUrl;
	private List<Reaction> reactions;	
	
}
