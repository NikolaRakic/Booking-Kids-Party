package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.exceptions.AccessException;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CooperationServiceImpl implements CooperationService {

	private final ServiceProviderRepository serviceProviderRepository;
	private final CooperationRepository cooperationRepository;
	private final ServiceProviderMapper serviceProviderMapper;
	private final CooperationMapper cooperationMapper;

	@Override
	public void add(CooperationRequestDTO cooperationDTOreq){
		ServiceProvider playroom = serviceProviderRepository.findById(cooperationDTOreq.getPlayroomId())
				.orElseThrow(() -> new IllegalArgumentException());
		ServiceProvider cooperationService = serviceProviderRepository
				.findById(cooperationDTOreq.getCooperationServiceId())
				.orElseThrow(() -> new IllegalArgumentException());
		User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Cooperation> cooperationOptional = cooperationRepository
				.findByPlayroomIdAndCooperationServiceId(playroom.getId(), cooperationService.getId());

		if(!loggedUser.getId().equals(playroom.getId()) && !loggedUser.getId().equals(cooperationService.getId())) {
			throw new AccessException("Only logged service provider can send requests for himself");
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
			if (playroom.getTypeOfServiceProvider() != TypeOfServiceProvider.PLAYROOM
					|| cooperationService.getTypeOfServiceProvider() == TypeOfServiceProvider.PLAYROOM) {
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
		if (serviceProvider.getTypeOfServiceProvider() == TypeOfServiceProvider.PLAYROOM) {
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
		
		if (serviceProvider.getTypeOfServiceProvider() == TypeOfServiceProvider.PLAYROOM) {
			return cooperationMapper.listToListDTO(cooperationRepository.findAllByPlayroomId(serviceProvider.getId()));
		} else {
			return cooperationMapper.listToListDTO(cooperationRepository.findAllByCooperationServiceId(serviceProvider.getId()));	
		}
		
	}

}
