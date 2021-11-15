package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceOfferDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceOfferDTOres;
import com.diplomski.bookingkidsparty.app.model.ServiceOffer;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;

@Component
public class ServiceOfferMapper {
	
	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	
	public ServiceOfferDTOres convertServiceOfferToServiceOfferDTO(ServiceOffer serviceOffer) {
		ServiceOfferDTOres serviceOfferDTOres = new ServiceOfferDTOres();
		serviceOfferDTOres.setId(serviceOffer.getId());
		serviceOfferDTOres.setStartDate(serviceOffer.getStartDate());
		serviceOfferDTOres.setEndDate(serviceOffer.getEndDate());
		serviceOfferDTOres.setMaxNumberOfKids(serviceOffer.getMaxNumberOfKids());
		serviceOfferDTOres.setMaxNumberOfAdults(serviceOffer.getMaxNumberOfAdults());
		serviceOfferDTOres.setName(serviceOffer.getName());
		serviceOfferDTOres.setPricePerHour(serviceOffer.getPricePerHour());
		serviceOfferDTOres.setServiceProviderId(serviceOffer.getServiceProvider().getId());
		return serviceOfferDTOres;
	}

	public List<ServiceOfferDTOres> convertListToListDTO(List<ServiceOffer> serviceOffers){
		List<ServiceOfferDTOres> serviceOffersDTOres = new ArrayList<ServiceOfferDTOres>();
		for (ServiceOffer serviceOffer : serviceOffers) {
			serviceOffersDTOres.add(convertServiceOfferToServiceOfferDTO(serviceOffer));
		}
		return serviceOffersDTOres;
	}
	
	public ServiceOffer convertServiceOfferDTOtoServiceOffer(ServiceOfferDTOreq serviceOfferDTOreq) {
		ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceOfferDTOreq.getServiceProviderId()).get();
		ServiceOffer serviceOffer = new ServiceOffer();
		serviceOffer.setEndDate(serviceOfferDTOreq.getEndDate());
		serviceOffer.setMaxNumberOfAdults(serviceOfferDTOreq.getMaxNumberOfAdults());
		serviceOffer.setMaxNumberOfKids(serviceOfferDTOreq.getMaxNumberOfKids());
		serviceOffer.setName(serviceOfferDTOreq.getName());
		serviceOffer.setPricePerHour(serviceOfferDTOreq.getPricePerHour());
		serviceOffer.setServiceProvider(serviceProvider);
		serviceOffer.setStartDate(serviceOfferDTOreq.getStartDate());
		
		return serviceOffer;
	}
}
