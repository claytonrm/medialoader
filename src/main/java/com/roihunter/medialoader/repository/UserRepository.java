package com.roihunter.medialoader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roihunter.medialoader.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByFacebookId(final String id);
	
}
