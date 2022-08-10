package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.ParentRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ParentResponseDTO;
import com.diplomski.bookingkidsparty.app.mapper.ParentMapper;
import com.diplomski.bookingkidsparty.app.model.Parent;
import com.diplomski.bookingkidsparty.app.repository.ParentRepository;
import com.diplomski.bookingkidsparty.app.service.ParentService;

import javassist.NotFoundException;

import javax.persistence.EntityNotFoundException;

@Service
public class ParentServiceImpl implements ParentService {

	@Autowired
	ParentRepository parentRepository;
	
	@Autowired
	ParentMapper parentMapper;
	
	@Override
	public ParentResponseDTO registration(ParentRequestDTO parentDTOreq) {
		Parent parent = parentMapper.DTOreqToEntity(parentDTOreq);
		Optional<Parent> parentOptional = parentRepository.findByUsernameOrEmail(parent.getUsername(), parent.getEmail());
		if(!parentOptional.isPresent()) {
			return parentMapper.EntityToDTOres(parentRepository.saveAndFlush(parent));
		}
		throw new IllegalArgumentException("User with this username or email arleady exists!");
	}

	@Override
	public ParentResponseDTO edit(UUID id, ParentRequestDTO parentDTOreq){
		Optional<Parent> parentOptional = parentRepository.findById(id);
		if(parentOptional.isPresent()) {
			Parent parentForEdit = parentOptional.get();
			//parentForEdit.setBlocked(parentDTOreq.isBlocked());
			parentForEdit.setEmail(parentDTOreq.getEmail());
			parentForEdit.setName(parentDTOreq.getName());
			//parentForEdit.setPassword(configuration.passwordEncoder().encode(userDTOreq.getPassword()));
			parentForEdit.setSurname(parentDTOreq.getSurname());
			parentForEdit.setTelephoneNumber(parentDTOreq.getTelephoneNumber());
			parentForEdit.setUsername(parentDTOreq.getUsername());
			
			return  parentMapper.EntityToDTOres(parentRepository.saveAndFlush(parentForEdit));
		}
			throw new EntityNotFoundException("Parent with id: " + id + " doesn't exists");
	}
	
	@Override
	public ParentResponseDTO findById(UUID id) {
		Parent parent = parentRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Parent with id: " + id + " doesn't exists"));
		return parentMapper.EntityToDTOres(parent);
	}

	@Override
	public List<ParentResponseDTO> findAll() {
		return parentMapper.ListToListDTO(parentRepository.findAll());
	}

}
