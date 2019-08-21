package com.roihunter.medialoader.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = -1897005470366463068L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("userId")
	private Long id;
	
	@JsonProperty("id")
	@NotNull
	@Column(name = "facebook_id")
	private String facebookId;
	
	@NotNull
	@Column(name = "name")
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(name = "profile_picture")
	private String profilePicture;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Photo> photos;
	
	public User(final String facebookId, final String name) {
		this.facebookId = facebookId;
		this.name = name;
	}
	
	public User(final List<Photo> photos) {
		this.photos = photos;
	}
	
	public User(final String facebookId, final String name, final Gender gender, final String profilePicture, final List<Photo> photos) {
		this.facebookId = facebookId;
		this.name = name;
		this.gender = gender;
		this.profilePicture = profilePicture;
		this.photos = photos;
	}
	
}
