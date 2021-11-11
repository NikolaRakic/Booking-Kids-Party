package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.model.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.TypeOfServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.service.TypeOfServiceProviderService;

@Service
public class TypeOfServiceProviderServiceImpl implements TypeOfServiceProviderService{

	@Autowired
	TypeOfServiceProviderRepository typeOfServiceProviderRepository;
	
	@Override
	public String add(TypeOfServiceProvider request) {
		typeOfServiceProviderRepository.saveAndFlush(request);
		return null;
	}

	@Override
	public List<TypeOfServiceProvider> getAll() {
		List<TypeOfServiceProvider> types = typeOfServiceProviderRepository.findAll();
		return types;
	}

}
