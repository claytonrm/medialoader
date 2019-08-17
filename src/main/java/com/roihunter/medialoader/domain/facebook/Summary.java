package com.roihunter.medialoader.domain.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Summary {

	@JsonProperty("total_count")
	private Long totalCount;
	
}
