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

	UUID add(ServiceOfferRequestDTO serviceOfferDTOreq) throws Exception;

	ServiceOfferResponseDTO findById(UUID id) throws Exception;

	boolean delete(UUID id);

	void edit(UUID id, ServiceOfferRequestDTO serviceOfferDTO) throws Exception;

	List<ServiceOfferResponseDTO> findAllByServiceProvider(UUID id) throws NotFoundException;

	List<ServiceOfferResponseDTO> findAllPlayroomByBookingDetails(String city, int numberOfKids, int numberOfAdults, LocalDate date, LocalTime startTime, LocalTime endTime) throws NotFoundException, Exception;

	List<ServiceOfferResponseDTO> findAdditionalOffersByPlayroom(UUID playroomOfferId, String additionalOffersType, String city,
			int numberOfKids, int numberOfAdults, LocalDate date, LocalTime startTime, LocalTime endTime) throws Exception;
}
