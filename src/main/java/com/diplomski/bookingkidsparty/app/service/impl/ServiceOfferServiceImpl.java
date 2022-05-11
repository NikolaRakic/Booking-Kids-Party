package com.diplomski.bookingkidsparty.app.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceOfferDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceOfferDTOres;
import com.diplomski.bookingkidsparty.app.mapper.ServiceOfferMapper;
import com.diplomski.bookingkidsparty.app.model.Reservation;
import com.diplomski.bookingkidsparty.app.model.ServiceOffer;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.ServiceOfferRepository;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.service.ServiceOfferService;
import com.diplomski.bookingkidsparty.app.util.OfferValidate;

import javassist.NotFoundException;

@Service
public class ServiceOfferServiceImpl implements ServiceOfferService{

	@Autowired
	ServiceOfferRepository serviceOfferRepository;
	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	@Autowired
	ServiceOfferMapper serviceOfferMapper;
	@Autowired
	OfferValidate offerValidate;
	
	@Override
	public List<ServiceOfferDTOres> findAll() {
		List<ServiceOffer> serviceOffers = serviceOfferRepository.findAll();
		return serviceOfferMapper.ListToListDTO(serviceOffers);
	}

	@Override
	public UUID add(ServiceOfferDTOreq serviceOfferDTOreq) throws Exception {
		ServiceOffer serviceOffer = serviceOfferMapper.dtoToEntity(serviceOfferDTOreq);
		offerValidate.dateValidate(serviceOffer);
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
			serviceOfferForEdit.setPricePerAdult(serviceOfferDTO.getPricePerAdult());
			serviceOfferForEdit.setPricePerKid(serviceOfferDTO.getPricePerKid());
			serviceOfferForEdit.setDescription(serviceOfferDTO.getDescription());
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

	@Override
	public List<ServiceOfferDTOres> findAllPlayroomByBookingDetails(String city, int numberOfKids, int numberOfAdults, 
			LocalDate date, LocalTime startTime, LocalTime endTime) throws Exception {

		if(startTime.isAfter(endTime) || startTime.equals(endTime)) {
			throw new Exception("End time must be after start time");
		}
		
		List<ServiceOffer> offers = serviceOfferRepository.findAllByBookingDetails(
				date, startTime, endTime, city, numberOfKids, numberOfAdults, TypeOfServiceProvider.IGRAONICA);
		
		//List<ServiceOffer> offersBetween = serviceOfferRepository.
		
		List<ServiceOffer> offersForDelete = new ArrayList<ServiceOffer>();
		

		
		
		
		//prolazi kroz sve ponude koje su aktivne dobijenog datuma
		for (ServiceOffer serviceOffer : offers) {
			//prolazi kroz sve rezervacije
			for (Reservation res : serviceOffer.getReservations()) {
				if(res.getDateOfReservation().equals(date)) {
					//ako postoji rezervacija za neku ponudu u istom terminu
					if(((res.getStartTime().isBefore(startTime) || res.getStartTime().equals(startTime)) && res.getEndTime().isAfter(startTime)) || 
							(res.getStartTime().isBefore(endTime) && (res.getEndTime().isAfter(endTime) || res.getEndTime().equals(endTime)))) {
						//prolazi opet kroz sve ponude
						for (ServiceOffer so : offers) {
							//pronalazi sve ponude te igraonice i brise iz liste
							if(so.getServiceProvider() == res.getServiceOffer().getServiceProvider()) {
								offersForDelete.add(so);
							}
						}
					}
				}
				
			}
		}
		
		offers.removeAll(offersForDelete);
		return serviceOfferMapper.ListToListDTO(offers);
	}

}
