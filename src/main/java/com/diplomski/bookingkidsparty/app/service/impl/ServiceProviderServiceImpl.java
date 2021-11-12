package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderDTOres;
import com.diplomski.bookingkidsparty.app.dto.response.TypeOfServiceProviderDTOres;
import com.diplomski.bookingkidsparty.app.mapper.ServiceProviderMapper;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.repository.TypeOfServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.service.ServiceProviderService;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	@Autowired
	ServiceProviderMapper serviceMapper;
	@Autowired
	TypeOfServiceProviderRepository typeOfServiceProviderRepository;
	
	@Override
	public UUID add(ServiceProviderDTOreq serviceProviderDTO) throws Exception {
		ServiceProvider serviceProvider = serviceMapper.convertServiceDTOreqToService(serviceProviderDTO);
		serviceProviderRepository.saveAndFlush(serviceProvider);
		return serviceProvider.getId();
	}

	@Override
	public List<ServiceProviderDTOres> findAll() {
		List<ServiceProvider> services = serviceProviderRepository.findAll();
		List<ServiceProviderDTOres> servicesDTO = serviceMapper.convertListToListDTO(services);
		return servicesDTO;
	}

	@Override
	public ServiceProviderDTOres findOne(UUID id) throws Exception {
		Optional<ServiceProvider> serviceProviderOptional = serviceProviderRepository.findById(id);
		if(!serviceProviderOptional.isPresent()) {
			throw new Exception("The type with this id does not exist!");
		}
		ServiceProviderDTOres serviceProviderDTO = serviceMapper
				.convertServiceToServiceDTOres(serviceProviderOptional.get());
		return serviceProviderDTO;
	}

	@Override
	public boolean delete(UUID id) {
		Optional<ServiceProvider> serviceProvider = serviceProviderRepository.findById(id);
		if(serviceProvider.isPresent()) {
			serviceProviderRepository.delete(serviceProvider.get());
			return true;
		}
		return false;
	}

	@Override
	public void edit(UUID id, ServiceProviderDTOres serviceProviderDTO) {
		try {
			Optional<ServiceProvider> serviceProviderOptional = serviceProviderRepository.findById(id);
			if(serviceProviderOptional.isPresent()) {
				ServiceProvider serviceProviderForEdit = serviceProviderOptional.get();
				serviceProviderForEdit.setName(serviceProviderDTO.getName());
				serviceProviderForEdit.setAccountNumber(serviceProviderDTO.getAccountNumber());
				serviceProviderForEdit.setAdress(serviceProviderDTO.getAdress());
				serviceProviderForEdit.setCity(serviceProviderDTO.getCity());
				serviceProviderForEdit.setEmail(serviceProviderDTO.getEmail());
				serviceProviderForEdit.setMaxNumberOfKids(serviceProviderDTO.getMaxNumberOfKids());
				serviceProviderForEdit.setPassword(serviceProviderDTO.getPassword());
				serviceProviderForEdit.setPib(serviceProviderDTO.getPib());
				serviceProviderForEdit.setTelephoneNumber(serviceProviderDTO.getTelephoneNumber());
				serviceProviderForEdit.setTypeOfServiceProvider(typeOfServiceProviderRepository.findById(
						serviceProviderDTO.getTypeOfServiceProviderId()).get());
				serviceProviderRepository.saveAndFlush(serviceProviderForEdit);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		
	}

}
