package com.roihunter.medialoader.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roihunter.medialoader.client.GraphAPI;
import com.roihunter.medialoader.domain.Photo;
import com.roihunter.medialoader.domain.User;
import com.roihunter.medialoader.domain.facebook.FacebookUser;
import com.roihunter.medialoader.repository.UserRepository;

@Service
public class UserService implements IService<User> {
	
	@Autowired
	private GraphAPI graphApi;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public User create(final String token) {
		final String[] fields = new String[] { "gender", "picture", "name" };
		final FacebookUser facebookUser = graphApi.getProfileInfo(fields, token);
		final List<Photo> userPhotos = photoService.getUserPhotos(token);
		final String profilePicureUrl = facebookUser.getPicture() != null ? facebookUser.getPicture().getData().getUrl() : null;
		final User user = new User(facebookUser.getId(), facebookUser.getName(), facebookUser.getGender(),  profilePicureUrl, userPhotos);

		delete(facebookUser.getId());
		
		return repository.save(user);
	}

	@Override
	public void delete(final String facebookId) {
		final User foundUser = get(facebookId);
		
		if (foundUser != null) {
			repository.deleteById(foundUser.getId());		
		}
	}
	
	@Override
	public User get(final String facebookId) {
		return repository.findByFacebookId(facebookId);
	}

	
	public User getPhotos(final String facebookId) {
		return new User(Optional.ofNullable(get(facebookId)).orElse(new User())
				.getPhotos());
	}

}
