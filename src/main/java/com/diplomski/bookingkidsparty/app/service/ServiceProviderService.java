package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderEditDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderOnePhotoResponseDTO;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;

import javassist.NotFoundException;

public interface ServiceProviderService {

	UUID add(ServiceProviderRequestDTO serviceProviderDTO) throws Exception;

	List<ServiceProviderOnePhotoResponseDTO> findAll();

	ServiceProviderResponseDTO findById(UUID id) throws Exception;

	void delete(UUID id);

	void edit(UUID id, ServiceProviderEditDTO serviceProviderDTO) throws NotFoundException;

	List<ServiceProviderOnePhotoResponseDTO> findAllByType(TypeOfServiceProvider typeOfServiceProvider) throws NotFoundException;

	String getType(UUID id) throws Exception;

}
