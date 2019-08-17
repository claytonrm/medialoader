package com.roihunter.medialoader.domain.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReactionsSummary {

	private String id;
	
	@JsonProperty("reactions_like")
	private FacebookReaction like;

	@JsonProperty("reactions_love")
	private FacebookReaction love;

	@JsonProperty("reactions_wow")
	private FacebookReaction wow;
	
	@JsonProperty("reactions_hahaha")
	private FacebookReaction hahaha;

	@JsonProperty("reactions_sad")
	private FacebookReaction sad;

	@JsonProperty("reactions_angry")
	private FacebookReaction angry;
	
}
