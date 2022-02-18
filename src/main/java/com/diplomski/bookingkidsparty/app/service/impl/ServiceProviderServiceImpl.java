package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderDTOres;
import com.diplomski.bookingkidsparty.app.mapper.ServiceProviderMapper;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.service.ServiceProviderService;
import com.diplomski.bookingkidsparty.app.util.TypeOfServiceProvider;

import javassist.NotFoundException;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	@Autowired
	ServiceProviderMapper serviceMapper;
	
	
	@Override
	public UUID add(ServiceProviderDTOreq serviceProviderDTO) throws Exception {
		ServiceProvider serviceProvider = serviceMapper.dtoReqToEntity(serviceProviderDTO);
		serviceProviderRepository.saveAndFlush(serviceProvider);
		return serviceProvider.getId();
	}

	@Override
	public List<ServiceProviderDTOres> findAll() {
		List<ServiceProvider> services = serviceProviderRepository.findAll();
		return serviceMapper.listToListDTO(services);
	}

	@Override
	public ServiceProviderDTOres findById(UUID id) throws Exception {
		Optional<ServiceProvider> serviceProviderOptional = serviceProviderRepository.findById(id);
		if(serviceProviderOptional.isPresent()) {
			ServiceProviderDTOres serviceProviderDTO = serviceMapper
					.entityToDTOres(serviceProviderOptional.get());
			return serviceProviderDTO;
		}
		throw new NotFoundException("ServiceProvider with this id doesn't exist!");
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
	public void edit(UUID id, ServiceProviderDTOres serviceProviderDTO) throws NotFoundException {
			Optional<ServiceProvider> serviceProviderOptional = serviceProviderRepository.findById(id);
			if(serviceProviderOptional.isPresent()) {
				ServiceProvider serviceProviderForEdit = serviceProviderOptional.get();
				serviceProviderForEdit.setName(serviceProviderDTO.getName());
				serviceProviderForEdit.setAccountNumber(serviceProviderDTO.getAccountNumber());
				serviceProviderForEdit.setAdress(serviceProviderDTO.getAdress());
				serviceProviderForEdit.setCity(serviceProviderDTO.getCity());
				serviceProviderForEdit.setEmail(serviceProviderDTO.getEmail());
				serviceProviderForEdit.setMaxNumberOfKids(serviceProviderDTO.getMaxNumberOfKids());
				serviceProviderForEdit.setPib(serviceProviderDTO.getPib());
				serviceProviderForEdit.setTelephoneNumber(serviceProviderDTO.getTelephoneNumber());
				//serviceProviderForEdit.setTypeOfServiceProvider(TypeOfServiceProvider.valueOf(serviceProviderDTO.getTypeOfServiceProvider()));
						
				serviceProviderRepository.saveAndFlush(serviceProviderForEdit);
			}else {
				throw new NotFoundException("ServiceProvider with this id doesn't exist!");
			}
	}

	@Override
	public List<ServiceProviderDTOres> findAllByType(String typeOfServiceProvider) throws NotFoundException {
		if(TypeOfServiceProvider.valueOf(typeOfServiceProvider.toUpperCase()) != null) {
			TypeOfServiceProvider type = TypeOfServiceProvider.valueOf(typeOfServiceProvider.toUpperCase());
			List<ServiceProvider> services = serviceProviderRepository.findAllByTypeOfServiceProvider(type);
			return serviceMapper.listToListDTO(services);
		}
		throw new NotFoundException("TypeOfServiceProvider with this type doesn't exist!");
	}

}
