package com.roihunter.medialoader.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reactions")
public class Reaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private ReactionType type;
	private Long value;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Photo photo;
	
	public Reaction(final ReactionType type, final Long value) {
		this.type = type;
		this.value = value;
	}
	
}
