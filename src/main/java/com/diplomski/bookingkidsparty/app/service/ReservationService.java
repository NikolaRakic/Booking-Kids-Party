package com.diplomski.bookingkidsparty.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.diplomski.bookingkidsparty.app.dto.request.ReservationRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.PageableResponse;
import com.diplomski.bookingkidsparty.app.dto.response.ReservationResponseDTO;

public interface ReservationService {

	UUID add(ReservationRequestDTO reservationDTOreq) throws Exception;

	PageableResponse getAll(Pageable pageable);

	List<ReservationResponseDTO> getAllByParty(UUID playRoomId, LocalDate dateOfReservation, LocalTime startTime);

	PageableResponse getAllByServisProvider(UUID serviceProviderId, int pageNo, int pageSize);

	PageableResponse getAllByUser(UUID userId, Pageable pageable);

	boolean delete(UUID id);

}
