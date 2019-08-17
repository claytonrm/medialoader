package com.roihunter.medialoader.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
	
	FEMALE("female"),
	MALE("male");
	
	private String description;
	
}
