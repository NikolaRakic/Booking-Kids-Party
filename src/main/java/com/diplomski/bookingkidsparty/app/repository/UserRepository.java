package com.diplomski.bookingkidsparty.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diplomski.bookingkidsparty.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

	User findByUsername(String username);

	Optional<User> findByUsernameOrEmail(String username, String email);

}
