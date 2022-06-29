package com.diplomski.bookingkidsparty.app.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.diplomski.bookingkidsparty.app.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID>{

	List<Reservation> findAllByDateOfReservationAndServiceOfferServiceProviderId(LocalDate dateOfReservation, UUID id);
	
	List<Reservation> findAllByPlayroomIdAndDateOfReservationAndStartTime(UUID playRoomId, LocalDate dateOfReservation, LocalTime startTime);

	Page<Reservation> findAllByServiceOfferServiceProviderId(UUID serviceProviderId, Pageable pagable);

	Page<Reservation> findAllByUserId(UUID userId, Pageable pagable);
	
	Page<Reservation> findAll(Pageable pagable);

}
