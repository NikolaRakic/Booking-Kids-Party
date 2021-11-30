package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
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
	@Autowired
	ModelMapper modelMapper;
	
	public ServiceOfferDTOres entityToDTO(ServiceOffer serviceOffer) {
		return modelMapper.map(serviceOffer, ServiceOfferDTOres.class);
	}

	public List<ServiceOfferDTOres> ListToListDTO(List<ServiceOffer> serviceOffers){
		List<ServiceOfferDTOres> serviceOffersDTOres = new ArrayList<ServiceOfferDTOres>();
		for (ServiceOffer serviceOffer : serviceOffers) {
			serviceOffersDTOres.add(entityToDTO(serviceOffer));
		}
		return serviceOffersDTOres;
	}
	
	public ServiceOffer dtoToEntity(ServiceOfferDTOreq serviceOfferDTOreq) {
		TypeMap<ServiceOfferDTOreq, ServiceOffer> typeMap = modelMapper.getTypeMap(ServiceOfferDTOreq.class, ServiceOffer.class);
		if(typeMap == null) {
			ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceOfferDTOreq.getServiceProviderId()).get();
			modelMapper.addMappings(new PropertyMap<ServiceOfferDTOreq, ServiceOffer>() {
	            @Override
	            protected void configure() {
	                skip(destination.getId());
	                map().setServiceProvider(serviceProvider);
	            }
	        });
		}
		return modelMapper.map(serviceOfferDTOreq, ServiceOffer.class);
	}
	
}
