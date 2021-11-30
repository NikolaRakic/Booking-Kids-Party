package com.diplomski.bookingkidsparty.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.ReservationDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ReservationDTOres;

public interface ReservationService {

	UUID add(ReservationDTOreq reservationDTOreq) throws Exception;

	List<ReservationDTOres> getAll();

	List<ReservationDTOres> getAllByParty(UUID playRoomId, LocalDate dateOfReservation, LocalTime startTime);

	List<ReservationDTOres> getAllByServisProvider(UUID serviceProviderId);

	List<ReservationDTOres> getAllByUser(UUID userId);

}
