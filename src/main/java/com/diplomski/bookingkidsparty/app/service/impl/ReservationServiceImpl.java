package com.diplomski.bookingkidsparty.app.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.ReservationDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ReservationDTOres;
import com.diplomski.bookingkidsparty.app.mapper.ReservationMapper;
import com.diplomski.bookingkidsparty.app.model.Reservation;
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
	public UUID add(ReservationDTOreq reservationDTOreq) throws Exception {
		Reservation newReservation = reservationMapper.dtoToEntity(reservationDTOreq);
		reservationValidate.requestValidation(newReservation);
		reservationRepository.saveAndFlush(newReservation);
		return newReservation.getId();
	}

	@Override
	public List<ReservationDTOres> getAll() {
		List<Reservation> reservations = reservationRepository.findAll();
		return reservationMapper.listToListDTO(reservations);
	}

	@Override
	public List<ReservationDTOres> getAllByParty(UUID playRoomId, LocalDate dateOfReservation, LocalTime startTime) {
		List<Reservation> reservations = reservationRepository
				.findAllByPlayroomIdAndDateOfReservationAndStartTime(playRoomId, dateOfReservation, startTime);
		return reservationMapper.listToListDTO(reservations);
	}

	@Override
	public List<ReservationDTOres> getAllByServisProvider(UUID serviceProviderId) {
		List<Reservation> reservations = reservationRepository.findAllByServiceOfferServiceProviderId(serviceProviderId);
		return reservationMapper.listToListDTO(reservations);
	}

	@Override
	public List<ReservationDTOres> getAllByUser(UUID userId) {
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
