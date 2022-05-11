package com.diplomski.bookingkidsparty.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.diplomski.bookingkidsparty.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

	User findByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.username = ?1 OR u.email = ?1")
	Optional<User> findByUsernameOrEmail(String usernameOrEmail);
	
	@Query("SELECT u FROM User u WHERE u.username = ?1 OR u.email = ?2")
	Optional<User> findByUsernameOrEmail(String username, String email);

}
