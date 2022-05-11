package com.diplomski.bookingkidsparty.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.diplomski.bookingkidsparty.app.model.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, UUID>{

	@Query("SELECT p FROM Parent p WHERE p.username = ?1 OR p.email = ?2")
	Optional<Parent> findByUsernameOrEmail(String username, String email);

}
