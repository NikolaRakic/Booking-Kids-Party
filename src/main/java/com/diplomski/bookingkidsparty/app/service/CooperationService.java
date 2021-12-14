package com.diplomski.bookingkidsparty.app.service;


import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.CooperationDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderDTOres;

public interface CooperationService {

	void add(CooperationDTOreq cooperationDTOreq) throws Exception;

	List<ServiceProviderDTOres> findAllByServiceProvider(UUID serviceProviderId);

	boolean delete(CooperationDTOreq cooperationDTOreq);

}
