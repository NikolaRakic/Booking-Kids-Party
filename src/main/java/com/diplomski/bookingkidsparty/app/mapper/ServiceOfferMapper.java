package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceOfferRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceOfferResponseDTO;
import com.diplomski.bookingkidsparty.app.model.ServiceOffer;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;

@Component
public class ServiceOfferMapper {
	
	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	@Autowired
	ModelMapper modelMapper;
	
	public ServiceOfferResponseDTO entityToDTO(ServiceOffer serviceOffer) {
		return modelMapper.map(serviceOffer, ServiceOfferResponseDTO.class);
	}

	public List<ServiceOfferResponseDTO> listToListDTO(List<ServiceOffer> serviceOffers){
		List<ServiceOfferResponseDTO> serviceOffersDTOres = new ArrayList<ServiceOfferResponseDTO>();
		System.out.println("mapper");
		for (ServiceOffer serviceOffer : serviceOffers) {
			System.out.println("for");
			serviceOffersDTOres.add(entityToDTO(serviceOffer));
		}
		System.out.println("return");
		return serviceOffersDTOres;
	}
	
	public ServiceOffer dtoToEntity(ServiceOfferRequestDTO serviceOfferDTOreq) {
		ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceOfferDTOreq.getServiceProviderId()).get();
		
		 if(serviceProvider.getTypeOfServiceProvider() == TypeOfServiceProvider.KETERING) {
			 serviceOfferDTOreq.setPricePerHour(0);
	         }else {
	        	 serviceOfferDTOreq.setPricePerAdult(0);
	        	 serviceOfferDTOreq.setPricePerKid(0);
	         }
		
		TypeMap<ServiceOfferRequestDTO, ServiceOffer> typeMap = modelMapper.getTypeMap(ServiceOfferRequestDTO.class, ServiceOffer.class);
		if(typeMap == null) {

			modelMapper.addMappings(new PropertyMap<ServiceOfferRequestDTO, ServiceOffer>() {
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
