package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceOfferDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceOfferDTOres;
import com.diplomski.bookingkidsparty.app.mapper.ServiceOfferMapper;
import com.diplomski.bookingkidsparty.app.model.ServiceOffer;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.ServiceOfferRepository;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.service.ServiceOfferService;

import javassist.NotFoundException;

@Service
public class ServiceOfferServiceImpl implements ServiceOfferService{

	@Autowired
	ServiceOfferRepository serviceOfferRepository;
	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	@Autowired
	ServiceOfferMapper serviceOfferMapper;
	
	@Override
	public List<ServiceOfferDTOres> findAll() {
		List<ServiceOffer> serviceOffers = serviceOfferRepository.findAll();
		return serviceOfferMapper.ListToListDTO(serviceOffers);
	}

	@Override
	public UUID add(ServiceOfferDTOreq serviceOfferDTOreq) {
		ServiceOffer serviceOffer = serviceOfferMapper.dtoToEntity(serviceOfferDTOreq);
		serviceOfferRepository.saveAndFlush(serviceOffer);
		return serviceOffer.getId();
	}

	@Override
	public ServiceOfferDTOres findById(UUID id) throws Exception {
		Optional<ServiceOffer> serviceOfferOptional = serviceOfferRepository.findById(id);
		if(serviceOfferOptional.isPresent()) {
			ServiceOfferDTOres serviceOfferDTOres = serviceOfferMapper
					.entityToDTO(serviceOfferOptional.get());
			return serviceOfferDTOres;
		}
		throw new Exception("ServiceOffer with this id doesn't exist!");
	}

	@Override
	public boolean delete(UUID id) {
		Optional<ServiceOffer> serviceOfferOptional = serviceOfferRepository.findById(id);
		if(serviceOfferOptional.isPresent()) {
			serviceOfferRepository.delete(serviceOfferOptional.get());
			return true;
		}
		return false;
	}

	@Override
	public void edit(UUID id, ServiceOfferDTOreq serviceOfferDTO) throws Exception {
		Optional<ServiceOffer> serviceOfferOptional = serviceOfferRepository.findById(id);
		if(serviceOfferOptional.isPresent()) {
			ServiceOffer serviceOfferForEdit = serviceOfferOptional.get();
			serviceOfferForEdit.setEndDate(serviceOfferDTO.getEndDate());
			serviceOfferForEdit.setMaxNumberOfAdults(serviceOfferDTO.getMaxNumberOfAdults());
			serviceOfferForEdit.setMaxNumberOfKids(serviceOfferDTO.getMaxNumberOfKids());
			serviceOfferForEdit.setName(serviceOfferDTO.getName());
			serviceOfferForEdit.setPricePerHour(serviceOfferDTO.getPricePerHour());
			serviceOfferForEdit.setStartDate(serviceOfferDTO.getStartDate());
			
			serviceOfferRepository.saveAndFlush(serviceOfferForEdit);
		}else {
			throw new NotFoundException("ServiceOffer with this id doesn't exist!");
		}
	}

	@Override
	public List<ServiceOfferDTOres> findAllByServiceProvider(UUID id) throws NotFoundException {
		Optional<ServiceProvider> serviceProviderOptional = serviceProviderRepository.findById(id);
		if(!serviceProviderOptional.isPresent()) {
			throw new NotFoundException("ServiceProvider with this id doesn't exist!");
		}
		List<ServiceOffer> serviceOffers = serviceOfferRepository.findAllByServiceProvider(serviceProviderOptional.get());
		return serviceOfferMapper.ListToListDTO(serviceOffers);
	}

}
