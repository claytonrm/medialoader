package com.roihunter.medialoader.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "photos")
public class Photo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("photoId")
	private Long id;
	
	@JsonProperty("id")
	@Column(name = "facebook_id")
	private String facebookId;
	
	@Column(name = "facebook_url")
	private String facebookUrl;
	
	@Column(name = "source_url")
	private String sourceUrl;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
	
	@OneToMany(mappedBy = "photo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reaction> reactions;	
	
}
