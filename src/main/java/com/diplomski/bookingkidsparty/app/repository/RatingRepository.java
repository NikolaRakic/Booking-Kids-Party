package com.diplomski.bookingkidsparty.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.diplomski.bookingkidsparty.app.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID>{

	List<Rating> findAllByReservationServiceOfferServiceProviderId(UUID serviceProviderId);

	Optional<Rating> findByReservationId(UUID reservationId);
	
	@Query(value = "SELECT count(rate), avg(rate) FROM Rating r WHERE r.reservation.serviceOffer.serviceProvider.id = ?1")
	List<Tuple> avg(UUID serviceProviderId);

}
