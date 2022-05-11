package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderDTOreq;
import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderEditDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderDTOres;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderOnePhotoDTOres;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;

import javassist.NotFoundException;

public interface ServiceProviderService {

	UUID add(ServiceProviderDTOreq serviceProviderDTO) throws Exception;

	List<ServiceProviderOnePhotoDTOres> findAll();

	ServiceProviderDTOres findById(UUID id) throws Exception;

	void delete(UUID id);

	void edit(UUID id, ServiceProviderEditDTO serviceProviderDTO) throws NotFoundException;

	List<ServiceProviderOnePhotoDTOres> findAllByType(TypeOfServiceProvider typeOfServiceProvider) throws NotFoundException;

	String getType(UUID id) throws Exception;

}
