package com.diplomski.bookingkidsparty.app.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, UUID>{
	
	List<ServiceProvider> findAllByTypeOfServiceProvider(TypeOfServiceProvider type);

//	List<ServiceProvider> findCooperationServiceByPlayRoomId(UUID serviceProviderId);
//
//	List<ServiceProvider> findCooperationServiceById(UUID serviceProviderId);

}
