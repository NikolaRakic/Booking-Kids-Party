package com.diplomski.bookingkidsparty.app.service;


import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.CooperationRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderOnePhotoResponseDTO;

public interface CooperationService {

	void add(CooperationRequestDTO cooperationDTOreq) throws Exception;

	List<ServiceProviderOnePhotoResponseDTO> findAllByServiceProvider(UUID serviceProviderId);

	boolean delete(CooperationRequestDTO cooperationDTOreq);

}
