package com.roihunter.medialoader.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private String id;
	private String name;
	private Gender gender;
	private String profilePicture;
	private List<Photo> photos;
	
}
