package com.diplomski.bookingkidsparty.app.service;


import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.CooperationRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderResponseDTO;
import com.diplomski.bookingkidsparty.app.model.Cooperation;
import com.diplomski.bookingkidsparty.app.dto.response.CooperationResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderOnePhotoResponseDTO;

public interface CooperationService {

	void add(CooperationRequestDTO cooperationDTOreq);

	List<ServiceProviderOnePhotoResponseDTO> findAllByServiceProvider(UUID serviceProviderId);

	boolean delete(CooperationRequestDTO cooperationDTOreq);

	List<CooperationResponseDTO> findAllByServiceProviderId(UUID serviceProviderId);

}
