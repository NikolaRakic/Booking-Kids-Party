package com.diplomski.bookingkidsparty.app.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.CooperationDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderDTOres;
import com.diplomski.bookingkidsparty.app.mapper.ServiceProviderMapper;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.service.CooperationService;

@Service
public class CooperationServiceImpl implements CooperationService {

	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	@Autowired
	ServiceProviderMapper serviceProviderMapper;
	
	@Override
	public void add(CooperationDTOreq cooperationDTOreq) throws Exception {
		Optional<ServiceProvider> playRoomOptional = serviceProviderRepository.findById(cooperationDTOreq.getPlayRoomId());
		Optional<ServiceProvider> cooperationServiceOptional = serviceProviderRepository.findById(cooperationDTOreq.getCooperationServiceId());
		if(playRoomOptional.isPresent() && cooperationServiceOptional.isPresent()) {
			ServiceProvider playRoom = playRoomOptional.get();
			ServiceProvider cooperationService = cooperationServiceOptional.get();
			if(playRoom.getTypeOfServiceProvider() == cooperationService.getTypeOfServiceProvider()) {
				throw new Exception("Two service provider must have different type!");
			}
			playRoom.getCooperationService().add(cooperationService);
			serviceProviderRepository.saveAndFlush(playRoom);
		}
		
	}

	@Override
	public List<ServiceProviderDTOres> findAllByServiceProvider(UUID serviceProviderId) {
		ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceProviderId).get();
		
		List<ServiceProvider> servicesProvider = new ArrayList<ServiceProvider>();
		if(!serviceProvider.getPlayRoom().isEmpty()) {
			for (ServiceProvider sp : serviceProvider.getPlayRoom()) {
				servicesProvider.add(sp);
			}
		}else if (!serviceProvider.getCooperationService().isEmpty()) {
			for(ServiceProvider sp : serviceProvider.getCooperationService()) {
				servicesProvider.add(sp);
			}
		}
		return serviceProviderMapper.listToListDTO(servicesProvider);
	}

	@Override
	public boolean delete(CooperationDTOreq cooperationDTOreq) {
		Optional<ServiceProvider> playRoomOptional = serviceProviderRepository.findById(cooperationDTOreq.getPlayRoomId());
		Optional<ServiceProvider> cooperationServiceOptional = serviceProviderRepository.findById(cooperationDTOreq.getCooperationServiceId());
		if(playRoomOptional.isPresent() && cooperationServiceOptional.isPresent() && playRoomOptional.get().getCooperationService().contains(cooperationServiceOptional.get())) {
			playRoomOptional.get().getCooperationService().remove(cooperationServiceOptional.get());
			serviceProviderRepository.saveAndFlush(playRoomOptional.get());
			return true;
		}
		return false;
	}

}
