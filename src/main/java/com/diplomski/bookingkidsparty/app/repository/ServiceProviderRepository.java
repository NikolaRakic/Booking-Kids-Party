package com.diplomski.bookingkidsparty.app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.TypeOfServiceProvider;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, UUID>{

	List<ServiceProvider> findAllByTypeOfServiceProvider(TypeOfServiceProvider typeOfServiceProvider);

}
