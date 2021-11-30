package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceOfferDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceOfferDTOres;

import javassist.NotFoundException;

public interface ServiceOfferService {

	List<ServiceOfferDTOres> findAll();

	UUID add(ServiceOfferDTOreq serviceOfferDTOreq) throws Exception;

	ServiceOfferDTOres findById(UUID id) throws Exception;

	boolean delete(UUID id);

	void edit(UUID id, ServiceOfferDTOreq serviceOfferDTO) throws Exception;

	List<ServiceOfferDTOres> findAllByServiceProvider(UUID id) throws NotFoundException;

}
