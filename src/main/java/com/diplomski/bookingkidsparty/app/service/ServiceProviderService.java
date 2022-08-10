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

	ServiceProviderResponseDTO add(ServiceProviderRequestDTO serviceProviderDTO);

	List<ServiceProviderOnePhotoResponseDTO> findAll();

	ServiceProviderResponseDTO findById(UUID id);

	void delete(UUID id);

	ServiceProviderResponseDTO edit(UUID id, ServiceProviderEditDTO serviceProviderDTO);

	List<ServiceProviderOnePhotoResponseDTO> findAllByType(TypeOfServiceProvider typeOfServiceProvider);

	String getType(UUID id);

}
