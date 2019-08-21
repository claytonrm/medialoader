package com.roihunter.medialoader.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roihunter.medialoader.client.GraphAPI;
import com.roihunter.medialoader.domain.Photo;
import com.roihunter.medialoader.domain.User;
import com.roihunter.medialoader.domain.facebook.FacebookUser;
import com.roihunter.medialoader.repository.UserRepository;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements IService<User> {
	
	@Autowired
	private GraphAPI graphApi;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public User create(final String token) throws AuthenticationException {
		final String[] fields = new String[] { "gender", "picture", "name" };
		
		try {
			final FacebookUser facebookUser = graphApi.getProfileInfo(fields, token);
			final List<Photo> userPhotos = photoService.getUserPhotos(token);
			final String profilePicureUrl = facebookUser.getPicture() != null ? facebookUser.getPicture().getData().getUrl() : null;
			final User user = new User(facebookUser.getId(), facebookUser.getName(), facebookUser.getGender(),  profilePicureUrl, userPhotos);
			
			delete(facebookUser.getId());
			return repository.save(user);

		} catch (FeignException e) {
			throw new AuthenticationException("Problem connecting on Graph API. Please check your token expiration.");
		} catch (Exception e) {
			throw new IllegalStateException("Problem connecting on database.");
		}
		
	}

	@Override
	public void delete(final String facebookId) {
		try {
			final User foundUser = get(facebookId);
			repository.deleteById(foundUser.getId());		
		} catch (EntityNotFoundException e) {
			log.warn("There's no user to delete.");
		}
	}
	
	@Override
	public User get(final String facebookId) {
		final User foundUser = repository.findByFacebookId(facebookId);
		if (foundUser == null) {
			throw new EntityNotFoundException(String.format("User %s not found.", facebookId));
		}
		return foundUser;
		
	}

	
	public User getPhotos(final String facebookId) {
		return new User(Optional.ofNullable(get(facebookId)).orElse(new User())
				.getPhotos());
	}

}
