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
import com.diplomski.bookingkidsparty.app.repository.ServiceOfferRepository;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.service.ServiceOfferService;

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
		return serviceOfferMapper.convertListToListDTO(serviceOffers);
	}

	@Override
	public UUID add(ServiceOfferDTOreq serviceOfferDTOreq) {
		ServiceOffer serviceOffer = serviceOfferMapper.convertServiceOfferDTOtoServiceOffer(serviceOfferDTOreq);
		serviceOfferRepository.saveAndFlush(serviceOffer);
		return serviceOffer.getId();
	}

	@Override
	public ServiceOfferDTOres findOne(UUID id) throws Exception {
		Optional<ServiceOffer> serviceOfferOptional = serviceOfferRepository.findById(id);
		if(!serviceOfferOptional.isPresent()) {
			throw new Exception("Offer with this id does not exist!");
		}
		ServiceOfferDTOres serviceOfferDTOres = serviceOfferMapper
				.convertServiceOfferToServiceOfferDTO(serviceOfferOptional.get());
		return serviceOfferDTOres;
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
	public void edit(UUID id, ServiceOfferDTOreq serviceOfferDTO) {
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
		}
	}

}
