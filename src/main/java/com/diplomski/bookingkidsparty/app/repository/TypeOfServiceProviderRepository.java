package com.diplomski.bookingkidsparty.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diplomski.bookingkidsparty.app.model.TypeOfServiceProvider;

@Repository
public interface TypeOfServiceProviderRepository extends JpaRepository<TypeOfServiceProvider, UUID>{

	Optional<TypeOfServiceProvider> findOneById(UUID id);

	Optional<TypeOfServiceProvider> findByName(String typeOfServiceProviderName);

	
}
