package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.ReservationDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ReservationDTOres;

public interface ReservationService {

	UUID add(ReservationDTOreq reservationDTOreq) throws Exception;

	List<ReservationDTOres> getAll();

}
