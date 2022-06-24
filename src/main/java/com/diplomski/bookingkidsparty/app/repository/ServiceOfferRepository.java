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
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;

@Repository
public interface ServiceOfferRepository extends JpaRepository<ServiceOffer, UUID>{

	List<ServiceOffer> findAllByServiceProvider(ServiceProvider serviceProvider);

	List<ServiceOffer> findAllByServiceProviderCity(String city);

	@Query(value = "SELECT s FROM ServiceOffer s WHERE ?1 BETWEEN s.startDate AND s.endDate AND"
			+ " ?2 >= s.serviceProvider.startOfWork AND ?2 < s.serviceProvider.endOfWork AND ?3 > s.serviceProvider.startOfWork AND "
			+ "?3 <= s.serviceProvider.endOfWork AND s.serviceProvider.city=?4 AND s.maxNumberOfKids >= ?5 AND s.maxNumberOfAdults >= ?6 AND s.serviceProvider.typeOfServiceProvider=?7")
	List<ServiceOffer> findAllByBookingDetails(LocalDate date, LocalTime startTime, LocalTime endTime, String city, int numberOfKids, int numberOfAdults, TypeOfServiceProvider type);
	
	@Query(value = "SELECT * FROM service_offer so JOIN reservation r ON so.id = r.service_offer_id JOIN service_provider sp ON sp.id = so.service_provider_id where sp.city = ?1 and date_of_reservation =?2 AND sp.id = r.playroom_id" + 
			" and (?3 <= r.start_time and ?4 >= r.start_time OR ?3 between r.start_time and r.end_time)", nativeQuery = true)
	List<ServiceOffer> findAllForRemoved(String city, LocalDate dateOfReservation, LocalTime startTime, LocalTime endTime);
	
	List<ServiceOffer> findAllByServiceProviderPlayRoomIdAndServiceProviderTypeOfServiceProviderAndServiceProviderCityAndServiceProviderMaxNumberOfKidsGreaterThanEqual(UUID playroomId, TypeOfServiceProvider typeOfServiceProvider, String city, int numberOfKids);

	List<ServiceOffer> findAllByServiceProviderTypeOfServiceProviderAndServiceProviderCityAndStartDateLessThanEqualAndEndDateGreaterThanEqual(TypeOfServiceProvider type, String city, LocalDate date1, LocalDate date2);
}
