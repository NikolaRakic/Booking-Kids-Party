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
import com.diplomski.bookingkidsparty.app.model.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.TypeOfServiceProviderRepository;

@Component
@Configuration
public class ServiceProviderMapper {

	@Autowired
	ModelMapper modelMapper;
	@Autowired
	TypeOfServiceProviderRepository typeOfServiceProviderRepository;
	
	
	public ServiceProvider convertServiceDTOreqToService(ServiceProviderDTOreq serviceProviderDTO) throws Exception {
		//ServiceProvider serviceProvider = modelMapper.map(serviceProviderDTO, ServiceProvider.class);
		TypeOfServiceProvider typeOfServiceProvider = new TypeOfServiceProvider();
		try {
			 typeOfServiceProvider = typeOfServiceProviderRepository
					.findById(serviceProviderDTO.getTypeOfServiceProviderId()).get();
			
		}catch (Exception e) {
			e.getMessage();
		}
		ServiceProvider serviceProvider = new ServiceProvider();
		serviceProvider.setName(serviceProviderDTO.getName());
		serviceProvider.setAccountNumber(serviceProviderDTO.getAccountNumber());
		serviceProvider.setEmail(serviceProviderDTO.getEmail());
		serviceProvider.setPassword(serviceProviderDTO.getPassword());
		serviceProvider.setPib(serviceProviderDTO.getPib());
		serviceProvider.setMaxNumberOfKids(serviceProviderDTO.getMaxNumberOfKids());
		serviceProvider.setTypeOfServiceProvider(typeOfServiceProvider);
		serviceProvider.setCity(serviceProviderDTO.getCity());
		serviceProvider.setAdress(serviceProviderDTO.getAdress());
		serviceProvider.setTelephoneNumber(serviceProviderDTO.getTelephoneNumber());
		return serviceProvider;
	}

	public ServiceProviderDTOres convertServiceToServiceDTOres(ServiceProvider serviceProvider) {
		ServiceProviderDTOres serviceProviderDTO = new ServiceProviderDTOres();
		
		serviceProviderDTO.setId(serviceProvider.getId());
		serviceProviderDTO.setName(serviceProvider.getName());
		serviceProviderDTO.setAccountNumber(serviceProvider.getAccountNumber());
		serviceProviderDTO.setEmail(serviceProvider.getEmail());
		serviceProviderDTO.setPassword(serviceProvider.getPassword());
		serviceProviderDTO.setPib(serviceProvider.getPib());
		serviceProviderDTO.setMaxNumberOfKids(serviceProvider.getMaxNumberOfKids());
		serviceProviderDTO.setTypeOfServiceProviderId(serviceProvider.getTypeOfServiceProvider().getId());
		serviceProviderDTO.setCity(serviceProvider.getCity());
		serviceProviderDTO.setAdress(serviceProvider.getAdress());
		serviceProviderDTO.setTelephoneNumber(serviceProvider.getTelephoneNumber());
				
		return serviceProviderDTO;
	}
	
	public List<ServiceProviderDTOres> convertListToListDTO(List<ServiceProvider> services){
		List<ServiceProviderDTOres> servicesDTO = new ArrayList<ServiceProviderDTOres>();
		for (ServiceProvider ServiceProvider : services) {
			servicesDTO.add(convertServiceToServiceDTOres(ServiceProvider));
		}
		return servicesDTO;
	}
}
