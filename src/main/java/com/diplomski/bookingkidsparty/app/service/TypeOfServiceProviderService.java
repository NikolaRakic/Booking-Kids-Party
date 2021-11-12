package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.TypeOfServiceProviderDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.TypeOfServiceProviderDTOres;

public interface TypeOfServiceProviderService {

	UUID add(TypeOfServiceProviderDTOreq typeOfServiceProviderDTO) throws Exception;

	List<TypeOfServiceProviderDTOres> findAll();

	TypeOfServiceProviderDTOres findOne(UUID id) throws Exception;

	boolean delete(UUID id);

	void edit(UUID id, TypeOfServiceProviderDTOreq typeOfServiceProviderDTO);


}
