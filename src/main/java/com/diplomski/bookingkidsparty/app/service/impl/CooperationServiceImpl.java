package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.CooperationRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.CooperationResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderOnePhotoResponseDTO;
import com.diplomski.bookingkidsparty.app.mapper.CooperationMapper;
import com.diplomski.bookingkidsparty.app.mapper.ServiceProviderMapper;
import com.diplomski.bookingkidsparty.app.model.Cooperation;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.CooperationRepository;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.service.CooperationService;

@Service
public class CooperationServiceImpl implements CooperationService {

	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	@Autowired
	CooperationRepository cooperationRepository;
	@Autowired
	ServiceProviderMapper serviceProviderMapper;
	@Autowired
	CooperationMapper cooperationMapper;

	@Override
	public void add(CooperationRequestDTO cooperationDTOreq) throws Exception {
		ServiceProvider playroom = serviceProviderRepository.findById(cooperationDTOreq.getPlayroomId())
				.orElseThrow(() -> new IllegalArgumentException());
		ServiceProvider cooperationService = serviceProviderRepository
				.findById(cooperationDTOreq.getCooperationServiceId())
				.orElseThrow(() -> new IllegalArgumentException());
		User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Cooperation> cooperationOptional = cooperationRepository
				.findByPlayroomIdAndCooperationServiceId(playroom.getId(), cooperationService.getId());

		System.out.println(loggedUser.getId());
		System.out.println(playroom.getId());
		System.out.println();
		System.out.println(loggedUser.getId());
		System.out.println(cooperationService.getId());
		
		if(!loggedUser.getId().equals(playroom.getId()) && !loggedUser.getId().equals(cooperationService.getId())) {
			throw new IllegalArgumentException("Logged service provider can only send requests for himself");
		}
		// confirm request for cooperation
		if (cooperationOptional.isPresent()) {
			Cooperation cooperation = cooperationOptional.get();
			

			if(!loggedUser.getId().equals(cooperation.getRequestSender())) {
				cooperation.setConfirmed(true);
				cooperationRepository.saveAndFlush(cooperation);
			}
			
		}
		// create new request for cooperation
		else {
			if (playroom.getTypeOfServiceProvider() != TypeOfServiceProvider.IGRAONICA
					|| cooperationService.getTypeOfServiceProvider() == TypeOfServiceProvider.IGRAONICA) {
				throw new IllegalArgumentException();
			}

			cooperationRepository.saveAndFlush(cooperationMapper.dtoToEntity(cooperationDTOreq));

		}

	}

	@Override
	public List<ServiceProviderOnePhotoResponseDTO> findAllByServiceProvider(UUID serviceProviderId) {
		ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceProviderId)
				.orElseThrow(() -> new IllegalArgumentException(
						"Service Provider with id " + serviceProviderId + " dosen't exist."));

		List<ServiceProvider> servicesProvider = new ArrayList<ServiceProvider>();
		if (serviceProvider.getTypeOfServiceProvider() == TypeOfServiceProvider.IGRAONICA) {
			cooperationRepository.findAllByPlayroomId(serviceProvider.getId()).forEach(
					cooperation -> servicesProvider.add(cooperation.getCooperationService()));
		} else {
			cooperationRepository.findAllByCooperationServiceId(serviceProvider.getId()).forEach(
					cooperation -> servicesProvider.add(cooperation.getCooperationService()));	
		}

		return serviceProviderMapper.listToListDTO(servicesProvider);
	}

	@Override
	public boolean delete(CooperationRequestDTO cooperationDTOreq) {
		ServiceProvider playroom = serviceProviderRepository.findById(cooperationDTOreq.getPlayroomId())
				.orElseThrow(() -> new IllegalArgumentException());
		ServiceProvider cooperationService = serviceProviderRepository
				.findById(cooperationDTOreq.getCooperationServiceId())
				.orElseThrow(() -> new IllegalArgumentException());
		Optional<Cooperation> cooperationOptional = cooperationRepository
				.findByPlayroomIdAndCooperationServiceId(playroom.getId(), cooperationService.getId());
		if (cooperationOptional.isPresent()) {
			cooperationRepository.delete(cooperationOptional.get());
			return true;
		}
		return false;
	}

	@Override
	public List<CooperationResponseDTO> findAllByServiceProviderId(UUID serviceProviderId) {
		ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceProviderId)
				.orElseThrow(() -> new IllegalArgumentException(
						"Service Provider with id " + serviceProviderId + " dosen't exist."));
		
		if (serviceProvider.getTypeOfServiceProvider() == TypeOfServiceProvider.IGRAONICA) {
			return cooperationMapper.listToListDTO(cooperationRepository.findAllByPlayroomId(serviceProvider.getId()));
		} else {
			return cooperationMapper.listToListDTO(cooperationRepository.findAllByCooperationServiceId(serviceProvider.getId()));	
		}
		
	}

}
