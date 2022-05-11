package com.diplomski.bookingkidsparty.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.diplomski.bookingkidsparty.app.model.Photo;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, UUID>{

	List<Photo> findAllByServiceProviderId(UUID serviceProviderId);
	
	@Query(value = "SELECT count(data) FROM Photo p WHERE p.serviceProvider = ?1")
	int getCountOfPhotosByServiceProvider(ServiceProvider serviceProvider);

}
