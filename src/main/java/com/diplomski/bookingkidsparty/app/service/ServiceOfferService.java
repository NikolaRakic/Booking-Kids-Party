package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceOfferDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceOfferDTOres;

public interface ServiceOfferService {

	List<ServiceOfferDTOres> findAll();

	UUID add(ServiceOfferDTOreq serviceOfferDTOreq);

	ServiceOfferDTOres findOne(UUID id) throws Exception;

	boolean delete(UUID id);

	void edit(UUID id, ServiceOfferDTOreq serviceOfferDTO) throws Exception;

}
