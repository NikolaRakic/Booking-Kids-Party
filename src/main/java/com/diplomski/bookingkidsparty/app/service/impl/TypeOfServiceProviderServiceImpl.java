package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.TypeOfServiceProviderDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.TypeOfServiceProviderDTOres;
import com.diplomski.bookingkidsparty.app.mapper.TypeOfServiceMapper;
import com.diplomski.bookingkidsparty.app.model.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.TypeOfServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.service.TypeOfServiceProviderService;

@Service
public class TypeOfServiceProviderServiceImpl implements TypeOfServiceProviderService{

	@Autowired
	TypeOfServiceProviderRepository typeOfServiceProviderRepository;
	@Autowired
	TypeOfServiceMapper typeOfServiceMapper;
	
	@Override
	public TypeOfServiceProviderDTOres findOne(UUID id) throws Exception {
		Optional<TypeOfServiceProvider> typeOfServiceProviderOptional = typeOfServiceProviderRepository.findById(id);
		if(!typeOfServiceProviderOptional.isPresent()) {
			throw new Exception("The type with this id does not exist!");
		}
		TypeOfServiceProviderDTOres typeOfServiceProviderDTO = typeOfServiceMapper
				.convertTypeOfServiceToTypeOfServiceDTO(typeOfServiceProviderOptional.get());
		return typeOfServiceProviderDTO;
	}
	
	@Override
	public List<TypeOfServiceProviderDTOres> findAll() {
		List<TypeOfServiceProvider> types = typeOfServiceProviderRepository.findAll();
		List<TypeOfServiceProviderDTOres> typesDTO = typeOfServiceMapper.convertListToListDTO(types);
		return typesDTO;
	}
	
	@Override
	public UUID add(TypeOfServiceProviderDTOreq typeOfServiceProviderDTO) throws Exception {
		TypeOfServiceProvider typeOfServiceProvider = typeOfServiceMapper.convertTypeOfServiceDTOtoTypeOfService(typeOfServiceProviderDTO);
		typeOfServiceProviderRepository.saveAndFlush(typeOfServiceProvider);
		return typeOfServiceProvider.getId();
	}

	@Override
	public boolean delete(UUID id) {
		Optional<TypeOfServiceProvider> typeOfServiceProvider = typeOfServiceProviderRepository.findById(id);
		if(typeOfServiceProvider.isPresent()) {
			typeOfServiceProviderRepository.delete(typeOfServiceProvider.get());
			return true;
		}
		return false;
	}

	@Override
	public void edit(UUID id, TypeOfServiceProviderDTOreq typeOfServiceProviderDTO) {
		try {
			Optional<TypeOfServiceProvider> typeOfServiceProviderOptional = typeOfServiceProviderRepository.findById(id);
			if(typeOfServiceProviderOptional.isPresent()) {
				TypeOfServiceProvider typeOfServiceProviderForEdit = typeOfServiceProviderOptional.get();
				typeOfServiceProviderForEdit.setName(typeOfServiceProviderDTO.getName());
				typeOfServiceProviderRepository.saveAndFlush(typeOfServiceProviderForEdit);
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
