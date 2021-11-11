package com.diplomski.bookingkidsparty.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.diplomski.bookingkidsparty.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

}
