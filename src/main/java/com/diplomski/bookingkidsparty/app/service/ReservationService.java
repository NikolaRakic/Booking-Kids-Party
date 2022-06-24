package com.diplomski.bookingkidsparty.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.ReservationRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ReservationResponseDTO;

public interface ReservationService {

	UUID add(ReservationRequestDTO reservationDTOreq) throws Exception;

	List<ReservationResponseDTO> getAll();

	List<ReservationResponseDTO> getAllByParty(UUID playRoomId, LocalDate dateOfReservation, LocalTime startTime);

	List<ReservationResponseDTO> getAllByServisProvider(UUID serviceProviderId);

	List<ReservationResponseDTO> getAllByUser(UUID userId);

	boolean delete(UUID id);

}
