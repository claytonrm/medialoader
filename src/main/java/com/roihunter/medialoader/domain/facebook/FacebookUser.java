package com.roihunter.medialoader.domain.facebook;

import com.roihunter.medialoader.domain.Gender;

import lombok.Data;

@Data
public class FacebookUser {

	private String id;
	private String name;
	private Gender gender;
	private ProfilePicture picture;
	
}
