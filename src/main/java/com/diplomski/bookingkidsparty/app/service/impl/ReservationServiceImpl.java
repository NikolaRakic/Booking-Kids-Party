package com.diplomski.bookingkidsparty.app.service.impl;

import java.time.LocalDate;
import java.util.List;
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

}
