package com.diplomski.bookingkidsparty.app.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diplomski.bookingkidsparty.app.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID>{

	List<Reservation> findAllByDateOfReservationAndServiceOfferServiceProviderId(LocalDate dateOfReservation, UUID id);

}
