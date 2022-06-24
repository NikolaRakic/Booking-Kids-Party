package com.diplomski.bookingkidsparty.app.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.ReservationRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ReservationResponseDTO;
import com.diplomski.bookingkidsparty.app.mapper.ReservationMapper;
import com.diplomski.bookingkidsparty.app.model.Reservation;
import com.diplomski.bookingkidsparty.app.model.ServiceOffer;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.ReservationRepository;
import com.diplomski.bookingkidsparty.app.service.ReservationService;
import com.diplomski.bookingkidsparty.app.util.ReservationValidate;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	ReservationMapper reservationMapper;
	@Autowired
	ReservationValidate reservationValidate;
	
	@Override
	public UUID add(ReservationRequestDTO reservationDTOreq) throws Exception {
		List<Reservation> newReservations = reservationMapper.dtoToEntity(reservationDTOreq);
		Reservation playroom = newReservations.stream().filter(sf -> sf.getServiceOffer().getServiceProvider().getTypeOfServiceProvider() == TypeOfServiceProvider.IGRAONICA)
		.findFirst().orElseThrow(() -> new IllegalArgumentException("Playroom must exist!"));
		reservationValidate.requestValidation(playroom);
		reservationRepository.saveAndFlush(playroom);
		newReservations.remove(playroom);
		for (Reservation reservation : newReservations) {
			reservationValidate.requestValidation(reservation);
			reservationRepository.saveAndFlush(reservation);
		}
		return playroom.getId();
	}

	@Override
	public List<ReservationResponseDTO> getAll() {
		List<Reservation> reservations = reservationRepository.findAll();
		return reservationMapper.listToListDTO(reservations);
	}

	@Override
	public List<ReservationResponseDTO> getAllByParty(UUID playRoomId, LocalDate dateOfReservation, LocalTime startTime) {
		List<Reservation> reservations = reservationRepository
				.findAllByPlayroomIdAndDateOfReservationAndStartTime(playRoomId, dateOfReservation, startTime);
		return reservationMapper.listToListDTO(reservations);
	}

	@Override
	public List<ReservationResponseDTO> getAllByServisProvider(UUID serviceProviderId) {
		List<Reservation> reservations = reservationRepository.findAllByServiceOfferServiceProviderId(serviceProviderId);
		return reservationMapper.listToListDTO(reservations);
	}

	@Override
	public List<ReservationResponseDTO> getAllByUser(UUID userId) {
		List<Reservation> reservations = reservationRepository.findAllByUserId(userId);
		return reservationMapper.listToListDTO(reservations);
	}

	@Override
	public boolean delete(UUID id) {
		Optional<Reservation> reservationOptional = reservationRepository.findById(id);
		if(reservationOptional.isPresent()) {
			reservationRepository.delete(reservationOptional.get());
			return true;
		}
		return false;
	}

}
