package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderOnePhotoResponseDTO;
import com.diplomski.bookingkidsparty.app.model.Photo;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.enums.Role;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.service.RatingService;

import javassist.NotFoundException;

@Component
@Configuration
public class ServiceProviderMapper {

	@Autowired
	ModelMapper modelMapper;
	//@Autowired
	//SecurityConfiguration configuration;
	@Autowired
	PhotoMapper photoMapper;
	
	@Autowired
	RatingService ratingService;
	
	
	public ServiceProvider dtoReqToEntity(ServiceProviderRequestDTO serviceProviderDTO){
		TypeMap<ServiceProviderRequestDTO, ServiceProvider> typeMap = modelMapper.getTypeMap(ServiceProviderRequestDTO.class, ServiceProvider.class);
		//String encodedPassword = configuration.passwordEncoder().encode(serviceProviderDTO.getPassword());
		try {
			TypeOfServiceProvider.valueOf(serviceProviderDTO.getTypeOfServiceProvider());
		}
		catch (Exception e) {
			throw new IllegalArgumentException("TypeOfServiceProvider is invalid");
		}
		
		if(typeMap == null) {
			modelMapper.addMappings(new PropertyMap<ServiceProviderRequestDTO, ServiceProvider>() {
	            @Override
	            protected void configure() {
	                skip(destination.getId());
	                map().setUserRole(Role.ROLE_SERVICE_PROVIDER);
	                //map().setPassword(encodedPassword);
	                //map().setTypeOfServiceProvider(type);
	            }
	        });
		}
			
		 return modelMapper.map(serviceProviderDTO, ServiceProvider.class);
	}

	public ServiceProviderResponseDTO entityToDTOres(ServiceProvider serviceProvider) {
		 return modelMapper.map(serviceProvider, ServiceProviderResponseDTO.class);
	}
	
	public ServiceProviderOnePhotoResponseDTO entityToDTOresWithOnePhoto(ServiceProvider serviceProvider) {
		Double averageRating = ratingService.getAverageRatingByServiceProvider(serviceProvider.getId()).getAverageRating();
		return new ServiceProviderOnePhotoResponseDTO(serviceProvider.getId(), serviceProvider.getUsername(),
				serviceProvider.getAccountNumber(), serviceProvider.getEmail(), serviceProvider.getPib(), serviceProvider.getStartOfWork(),
				serviceProvider.getEndOfWork(), serviceProvider.getMaxNumberOfKids(), serviceProvider.getTypeOfServiceProvider().name(),
				serviceProvider.getCity(), serviceProvider.getAdress(), serviceProvider.getTelephoneNumber(),
				photoMapper.entityToDto(serviceProvider.getPhotos().stream().findFirst().orElse(new Photo())), averageRating);
	}
	
	public List<ServiceProviderOnePhotoResponseDTO> listToListDTO(List<ServiceProvider> services){
		List<ServiceProviderOnePhotoResponseDTO> servicesDTO = new ArrayList<ServiceProviderOnePhotoResponseDTO>();
		for (ServiceProvider serviceProvider : services) {
			servicesDTO.add(entityToDTOresWithOnePhoto(serviceProvider));
		}
		return servicesDTO;
	}
}
