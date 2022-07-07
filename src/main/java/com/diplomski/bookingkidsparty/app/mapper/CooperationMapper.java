package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.CooperationRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.CooperationResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ParentResponseDTO;
import com.diplomski.bookingkidsparty.app.model.Cooperation;
import com.diplomski.bookingkidsparty.app.model.Parent;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;

@Component
public class CooperationMapper {
	
	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	
	@Autowired
	ModelMapper modelMapper;

	public Cooperation dtoToEntity(CooperationRequestDTO cooperationDto) {
		ServiceProvider playroom = serviceProviderRepository.getById(cooperationDto.getPlayroomId());
		ServiceProvider cooperationsService = serviceProviderRepository.getById(cooperationDto.getCooperationServiceId());
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return new Cooperation(null, playroom, cooperationsService, false, currentUser.getId());
	}
	
	public CooperationResponseDTO EntityToDTOres(Cooperation cooperation) {
		return modelMapper.map(cooperation, CooperationResponseDTO.class);
	}
	
	public List<CooperationResponseDTO> listToListDTO(List<Cooperation> cooperations){
		List<CooperationResponseDTO> cooperationsDTO = new ArrayList<>();
		cooperations.forEach(cooperation -> cooperationsDTO.add(EntityToDTOres(cooperation)));
		return cooperationsDTO;
	}
}
