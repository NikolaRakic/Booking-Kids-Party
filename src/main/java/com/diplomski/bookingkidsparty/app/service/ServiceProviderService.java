package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderDTOres;

import javassist.NotFoundException;

public interface ServiceProviderService {

	UUID add(ServiceProviderDTOreq serviceProviderDTO) throws Exception;

	List<ServiceProviderDTOres> findAll();

	ServiceProviderDTOres findById(UUID id) throws Exception;

	boolean delete(UUID id);

	void edit(UUID id, ServiceProviderDTOres serviceProviderDTO) throws NotFoundException;

	List<ServiceProviderDTOres> findAllByType(String typeOfServiceProvider) throws NotFoundException;

}
