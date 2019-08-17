package com.roihunter.medialoader.domain;

import lombok.Getter;

@Getter
public enum ReactionType {

	LIKE("like", "reactions.type(LIKE).limit(0).summary(total_count).as(reactions_like)"), 
	LOVE("love", "reactions.type(LOVE).limit(0).summary(total_count).as(reactions_love)"), 
	WOW("wow", "reactions.type(WOW).limit(0).summary(total_count).as(reactions_wow)"), 
	HAHA("haha", "reactions.type(HAHA).limit(0).summary(total_count).as(reactions_hahaha)"), 
	SAD("sad", "reactions.type(SAD).limit(0).summary(total_count).as(reactions_sad)"), 
	ANGRY("angry", "reactions.type(ANGRY).limit(0).summary(total_count).as(reactions_angry)");
	
	private String description;
	private String query;
	
	private ReactionType(final String description, final String query) {
		this.description = description;
		this.query = query;
	}
	
}
