package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderDTOres;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;

@Component
@Configuration
public class ServiceProviderMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public ServiceProvider dtoReqToEntity(ServiceProviderDTOreq serviceProviderDTO) throws Exception {
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
