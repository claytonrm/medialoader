package com.roihunter.medialoader.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {
	
	private ReactionType type;
	private Long value;
	
}
