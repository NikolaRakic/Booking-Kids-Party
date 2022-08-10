package com.diplomski.bookingkidsparty.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceOfferRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceOfferResponseDTO;

import javassist.NotFoundException;

public interface ServiceOfferService {

	List<ServiceOfferResponseDTO> findAll();

	ServiceOfferResponseDTO add(ServiceOfferRequestDTO serviceOfferDTOreq);

	ServiceOfferResponseDTO findById(UUID id);

	boolean delete(UUID id);

	ServiceOfferResponseDTO edit(UUID id, ServiceOfferRequestDTO serviceOfferDTO);

	List<ServiceOfferResponseDTO> findAllByServiceProvider(UUID id);

	List<ServiceOfferResponseDTO> findAllPlayroomByBookingDetails(String city, int numberOfKids, int numberOfAdults, LocalDate date, LocalTime startTime, LocalTime endTime);

	List<ServiceOfferResponseDTO> findAdditionalOffersByPlayroom(UUID playroomOfferId, String additionalOffersType, String city,
			int numberOfKids, int numberOfAdults, LocalDate date, LocalTime startTime, LocalTime endTime);
}
