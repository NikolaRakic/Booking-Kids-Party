package com.diplomski.bookingkidsparty.app.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.diplomski.bookingkidsparty.app.model.ServiceOffer;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.util.TypeOfServiceProvider;

@Repository
public interface ServiceOfferRepository extends JpaRepository<ServiceOffer, UUID>{

	List<ServiceOffer> findAllByServiceProvider(ServiceProvider serviceProvider);

	List<ServiceOffer> findAllByServiceProviderCity(String city);

	@Query(value = "SELECT s FROM ServiceOffer s WHERE ?1 BETWEEN s.startDate AND s.endDate AND"
			+ " ?2 BETWEEN s.serviceProvider.startOfWork AND s.serviceProvider.endOfWork AND ?3 BETWEEN s.serviceProvider.startOfWork AND "
			+ "s.serviceProvider.endOfWork AND s.serviceProvider.city=?4 AND s.maxNumberOfKids >= ?5 AND s.maxNumberOfAdults >= ?6 AND s.serviceProvider.typeOfServiceProvider=?7")
	List<ServiceOffer> findAllByBookingDetails(LocalDate date, LocalTime startTime, LocalTime endTime, String city, int numberOfKids, int numberOfAdults, TypeOfServiceProvider type);

}
