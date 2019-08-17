package com.roihunter.medialoader.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Gender {
	
	FEMALE("female"),
	MALE("male");
	
	private String description;
	
	private Gender(final String description) {
		this.description = description;
	}
	
	@JsonValue
    final String value() {
        return this.description;
    }
}
