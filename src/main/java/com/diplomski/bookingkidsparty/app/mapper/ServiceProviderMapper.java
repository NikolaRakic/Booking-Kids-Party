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
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.TypeOfServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.security.SecurityConfiguration;

@Component
@Configuration
public class ServiceProviderMapper {

	@Autowired
	ModelMapper modelMapper;
	@Autowired
	SecurityConfiguration configuration;
	@Autowired
	TypeOfServiceProviderRepository typeOfServiceProviderRepository;
	
	public ServiceProvider dtoReqToEntity(ServiceProviderDTOreq serviceProviderDTO) throws Exception {
    	String encodedPassword = configuration.passwordEncoder().encode(serviceProviderDTO.getPassword());
		modelMapper.addMappings(new PropertyMap<ServiceProviderDTOreq, ServiceProvider>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                map().setPassword(encodedPassword);
            }
        });
		 return modelMapper.map(serviceProviderDTO, ServiceProvider.class);
	}

	public ServiceProviderDTOres entityToDTOres(ServiceProvider serviceProvider) {
		 return modelMapper.map(serviceProvider, ServiceProviderDTOres.class);
	}
	
	public List<ServiceProviderDTOres> ListToListDTO(List<ServiceProvider> services){
		List<ServiceProviderDTOres> servicesDTO = new ArrayList<ServiceProviderDTOres>();
		for (ServiceProvider ServiceProvider : services) {
			servicesDTO.add(entityToDTOres(ServiceProvider));
		}
		return servicesDTO;
	}
}
