package com.roihunter.medialoader.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.roihunter.medialoader.domain.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository repository;
	
	@Test
	public void findByFacebookId_shouldFindUserByFacebookId() throws JsonParseException, JsonMappingException, IOException {
		entityManager.persist(new User("12345678", "Eddie Vedder"));

		final User user = repository.findByFacebookId("12345678");
		
		assertThat(user.getId()).isNotNull();
		assertThat(user.getFacebookId()).isEqualTo("12345678");
		assertThat(user.getName()).isEqualTo("Eddie Vedder");
	}

	@Test
	public void findByFacebookId_shouldNotFindUserByFacebookId() throws JsonParseException, JsonMappingException, IOException {
		entityManager.persist(new User("12345678", "Eddie Vedder"));
		
		final User user = repository.findByFacebookId("343");
		
		assertThat(user).isNull();
	}

}
