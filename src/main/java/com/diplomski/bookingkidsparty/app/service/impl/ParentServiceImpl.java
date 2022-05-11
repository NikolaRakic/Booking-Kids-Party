package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.ParentDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ParentDTOres;
import com.diplomski.bookingkidsparty.app.mapper.ParentMapper;
import com.diplomski.bookingkidsparty.app.model.Parent;
import com.diplomski.bookingkidsparty.app.repository.ParentRepository;
import com.diplomski.bookingkidsparty.app.service.ParentService;

import javassist.NotFoundException;

@Service
public class ParentServiceImpl implements ParentService {

	@Autowired
	ParentRepository parentRepository;
	
	@Autowired
	ParentMapper parentMapper;
	
	@Override
	public UUID registration(ParentDTOreq parentDTOreq) throws Exception {
		Parent parent = parentMapper.DTOreqToEntity(parentDTOreq);
		Optional<Parent> parentOptional = parentRepository.findByUsernameOrEmail(parent.getUsername(), parent.getEmail());
		if(!parentOptional.isPresent()) {
			parentRepository.saveAndFlush(parent);
			return parent.getId();
		}
		throw new Exception("User with this username or email arleady exist!");
	}

	@Override
	public void edit(UUID id, ParentDTOreq parentDTOreq) throws NotFoundException {
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
			
			parentRepository.saveAndFlush(parentForEdit);
		}else {
			throw new NotFoundException("User with this id doesn't exist!");
		}
	}
	
	@Override
	public ParentDTOres findById(UUID id) throws NotFoundException {
		Optional<Parent> parentOptional = parentRepository.findById(id);
		if(parentOptional.isPresent()) {
			return parentMapper.EntityToDTOres(parentOptional.get());
		}
		throw new NotFoundException("User with this id doesn't exist!");
	}

	@Override
	public List<ParentDTOres> findAll() {
		return parentMapper.ListToListDTO(parentRepository.findAll());
	}

}
