package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceOfferRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.PhotoResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.RatingResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceOfferResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.StarRatingResponseDTO;
import com.diplomski.bookingkidsparty.app.model.ServiceOffer;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.service.RatingService;

@Component
public class ServiceOfferMapper {
	
	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	RatingService ratingService;
	@Autowired
	PhotoMapper photoMapper;
	
	public ServiceOfferResponseDTO entityToDTO(ServiceOffer serviceOffer) {
		Double averageRating = ratingService.getAverageRatingByServiceProvider(serviceOffer.getServiceProvider().getId()).getAverageRating();
		Set<PhotoResponseDTO> photoDTO = photoMapper.listToListDTO(serviceOffer.getServiceProvider().getPhotos());
		
		ServiceOfferResponseDTO serviceOfferDTO = new ServiceOfferResponseDTO();
		serviceOfferDTO.setDescription(serviceOffer.getDescription());
		serviceOfferDTO.setEndDate(serviceOffer.getEndDate());
		serviceOfferDTO.setId(serviceOffer.getId());
		serviceOfferDTO.setMaxNumberOfAdults(serviceOffer.getMaxNumberOfAdults());
		serviceOfferDTO.setMaxNumberOfKids(serviceOffer.getMaxNumberOfKids());
		serviceOfferDTO.setName(serviceOffer.getName());
		serviceOfferDTO.setPricePerAdult(serviceOffer.getPricePerAdult());
		serviceOfferDTO.setPricePerHour(serviceOffer.getPricePerHour());
		serviceOfferDTO.setPricePerKid(serviceOffer.getPricePerKid());
		serviceOfferDTO.setAverageRating(averageRating);
		serviceOfferDTO.setServiceProviderAdress(serviceOffer.getServiceProvider().getAdress());
		serviceOfferDTO.setServiceProviderCity(serviceOffer.getServiceProvider().getCity());
		serviceOfferDTO.setServiceProviderId(serviceOffer.getServiceProvider().getId());
		serviceOfferDTO.setServiceProviderPhotos(photoDTO);
		serviceOfferDTO.setServiceProviderUsername(serviceOffer.getServiceProvider().getUsername());
		serviceOfferDTO.setStartDate(serviceOffer.getStartDate());
		
		return serviceOfferDTO;
	}

	public List<ServiceOfferResponseDTO> listToListDTO(List<ServiceOffer> serviceOffers){
		List<ServiceOfferResponseDTO> serviceOffersDTOres = new ArrayList<ServiceOfferResponseDTO>();
		for (ServiceOffer serviceOffer : serviceOffers) {
			serviceOffersDTOres.add(entityToDTO(serviceOffer));
		}
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
