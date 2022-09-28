package com.diplomski.bookingkidsparty.app.util;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.model.Reservation;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.ReservationRepository;

@Component
@RequiredArgsConstructor
public class ReservationValidate {
	
	private final ReservationRepository reservationRepository;
	
	public void requestValidation(Reservation newReservation) {
		if(newReservation.getDateOfReservation().isBefore(LocalDate.now())){
			throw new IllegalArgumentException("DateOfReservation is in the past");
		}
		if(newReservation.getEndTime().isBefore(newReservation.getStartTime()) || newReservation.getEndTime().equals(newReservation.getStartTime())) {
			throw new IllegalArgumentException("EndTime is must be after the startTime");
		}
		if(newReservation.getNumberOfAdults() > newReservation.getServiceOffer().getMaxNumberOfAdults()) {
			throw new IllegalArgumentException("NumberOfAdults is greater than MaxNumberOfAdults");
		}
		if(newReservation.getNumberOfKids() > newReservation.getServiceOffer().getMaxNumberOfKids()){
			throw new IllegalArgumentException("NumberOfKids is greater than maxNumberOfKids");
		}
		if(newReservation.getDateOfReservation().isBefore(newReservation.getServiceOffer().getStartDate()) ||
				newReservation.getDateOfReservation().isAfter(newReservation.getServiceOffer().getEndDate())){
				throw new IllegalArgumentException("The offer is not valid for a this date");
			}
		if(newReservation.getServiceOffer().getServiceProvider().getTypeOfServiceProvider() == TypeOfServiceProvider.PLAYROOM) {
			validateForPlayroom(newReservation);
		}
		else {
			//provera da li postoji rezervisana igraonica za dati termin
			List<Reservation> res = reservationRepository.findAllByPlayroomIdAndDateOfReservationAndStartTime(newReservation.getPlayroom().getId(),
					newReservation.getDateOfReservation(), newReservation.getStartTime());
			if(res.isEmpty()) {
				throw new IllegalArgumentException("Reservation for playroom doesn't exist");
			}
		}
		return;
	}

	private void validateForPlayroom(Reservation newReservation) {
		List<Reservation> existsReservationsByDay = reservationRepository
				.findAllByDateOfReservationAndServiceOfferServiceProviderId(newReservation.getDateOfReservation(), newReservation.getServiceOffer().getServiceProvider().getId());

		if(newReservation.getStartTime().isBefore(newReservation.getServiceOffer().getServiceProvider().getStartOfWork()) ||
				newReservation.getEndTime().isAfter(newReservation.getServiceOffer().getServiceProvider().getEndOfWork())){
				throw new IllegalArgumentException("The playroom is closed at that time");
			}
	
		//ako postoji rezervacija za dan nove rezervacije
		if(!existsReservationsByDay.isEmpty()) {
			//provera da li se pokloapaju vremena	
			for (Reservation res : existsReservationsByDay) {
				if((newReservation.getStartTime().isAfter(res.getStartTime()) || newReservation.getStartTime().equals(res.getStartTime())) 
						&& newReservation.getStartTime().isBefore(res.getEndTime()) ||
						newReservation.getStartTime().isBefore(res.getStartTime()) && newReservation.getEndTime().isAfter(res.getStartTime()))
				{
					throw new IllegalArgumentException("Reservation arleady exists as that time");
				}
			}
		}
		return;
	}	
}
