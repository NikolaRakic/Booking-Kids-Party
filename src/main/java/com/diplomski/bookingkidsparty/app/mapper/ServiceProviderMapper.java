package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderDTOres;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderOnePhotoDTOres;
import com.diplomski.bookingkidsparty.app.model.Photo;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.enums.Role;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;

@Component
@Configuration
public class ServiceProviderMapper {

	@Autowired
	ModelMapper modelMapper;
	//@Autowired
	//SecurityConfiguration configuration;
	@Autowired
	PhotoMapper photoMapper;
	
	
	public ServiceProvider dtoReqToEntity(ServiceProviderDTOreq serviceProviderDTO) throws Exception {
		TypeMap<ServiceProviderDTOreq, ServiceProvider> typeMap = modelMapper.getTypeMap(ServiceProviderDTOreq.class, ServiceProvider.class);
		//String encodedPassword = configuration.passwordEncoder().encode(serviceProviderDTO.getPassword());
		try {
			TypeOfServiceProvider.valueOf(serviceProviderDTO.getTypeOfServiceProvider());
		}
		catch (Exception e) {
			throw new Exception("TypeOfServiceProvider is invalid");
		}
		
		if(typeMap == null) {
			modelMapper.addMappings(new PropertyMap<ServiceProviderDTOreq, ServiceProvider>() {
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

	public ServiceProviderDTOres entityToDTOres(ServiceProvider serviceProvider) {
		 return modelMapper.map(serviceProvider, ServiceProviderDTOres.class);
	}
	
	public ServiceProviderOnePhotoDTOres entityToDTOresWithOnePhoto(ServiceProvider serviceProvider) {
		return new ServiceProviderOnePhotoDTOres(serviceProvider.getId(), serviceProvider.getUsername(),
				serviceProvider.getAccountNumber(), serviceProvider.getEmail(), serviceProvider.getPib(), serviceProvider.getStartOfWork(),
				serviceProvider.getEndOfWork(), serviceProvider.getMaxNumberOfKids(), serviceProvider.getTypeOfServiceProvider().name(),
				serviceProvider.getCity(), serviceProvider.getAdress(), serviceProvider.getTelephoneNumber(),
				photoMapper.entityToDto(serviceProvider.getPhotos().stream().findFirst().orElse(new Photo())));
	}
	
	public List<ServiceProviderOnePhotoDTOres> listToListDTO(List<ServiceProvider> services){
		List<ServiceProviderOnePhotoDTOres> servicesDTO = new ArrayList<ServiceProviderOnePhotoDTOres>();
		for (ServiceProvider serviceProvider : services) {
			servicesDTO.add(entityToDTOresWithOnePhoto(serviceProvider));
		}
		return servicesDTO;
	}
}
