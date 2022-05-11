package com.diplomski.bookingkidsparty.app.service;


import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.CooperationDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderDTOres;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderOnePhotoDTOres;

public interface CooperationService {

	void add(CooperationDTOreq cooperationDTOreq) throws Exception;

	List<ServiceProviderOnePhotoDTOres> findAllByServiceProvider(UUID serviceProviderId);

	boolean delete(CooperationDTOreq cooperationDTOreq);

}
