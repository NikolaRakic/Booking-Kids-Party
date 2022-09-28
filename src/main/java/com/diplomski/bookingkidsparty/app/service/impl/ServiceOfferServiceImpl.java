package com.diplomski.bookingkidsparty.app.service.impl;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceOfferRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceOfferResponseDTO;
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

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ServiceOfferServiceImpl implements ServiceOfferService {

    private final ServiceOfferRepository serviceOfferRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final ServiceOfferMapper serviceOfferMapper;

    @Override
    public List<ServiceOfferResponseDTO> findAll() {
        List<ServiceOffer> serviceOffers = serviceOfferRepository.findAll();
        return serviceOfferMapper.listToListDTO(serviceOffers);
    }

    @Override
    public ServiceOfferResponseDTO add(ServiceOfferRequestDTO serviceOfferDTOreq) {
        ServiceOffer serviceOffer = serviceOfferMapper.dtoToEntity(serviceOfferDTOreq);
        OfferValidate.dateValidate(serviceOffer);
        return serviceOfferMapper.entityToDTO(serviceOfferRepository.saveAndFlush(serviceOffer));
    }

    @Override
    public ServiceOfferResponseDTO findById(UUID id) {
        return serviceOfferMapper.entityToDTO(serviceOfferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service offer with id " + id + " is not found")));
    }

    @Override
    public boolean delete(UUID id) {
        Optional<ServiceOffer> serviceOfferOptional = serviceOfferRepository.findById(id);
        if (serviceOfferOptional.isPresent()) {
            serviceOfferRepository.delete(serviceOfferOptional.get());
            return true;
        }
        return false;
    }

    @Override
    public ServiceOfferResponseDTO edit(UUID id, ServiceOfferRequestDTO serviceOfferDTO) {
        Optional<ServiceOffer> serviceOfferOptional = serviceOfferRepository.findById(id);
        if (serviceOfferOptional.isPresent()) {
            ServiceOffer serviceOfferForEdit = serviceOfferOptional.get();
            serviceOfferForEdit.setEndDate(serviceOfferDTO.getEndDate());
            serviceOfferForEdit.setMaxNumberOfAdults(serviceOfferDTO.getMaxNumberOfAdults());
            serviceOfferForEdit.setMaxNumberOfKids(serviceOfferDTO.getMaxNumberOfKids());
            serviceOfferForEdit.setName(serviceOfferDTO.getName());
            serviceOfferForEdit.setPricePerAdult(serviceOfferDTO.getPricePerAdult());
            serviceOfferForEdit.setPricePerKid(serviceOfferDTO.getPricePerKid());
            serviceOfferForEdit.setDescription(serviceOfferDTO.getDescription());
            serviceOfferForEdit.setStartDate(serviceOfferDTO.getStartDate());

            return serviceOfferMapper.entityToDTO(serviceOfferRepository.saveAndFlush(serviceOfferForEdit));
        } else {
            throw new EntityNotFoundException("ServiceOffer with this id doesn't exist!");
        }
    }

    @Override
    public List<ServiceOfferResponseDTO> findAllByServiceProvider(UUID id) {
        Optional<ServiceProvider> serviceProviderOptional = serviceProviderRepository.findById(id);
        if (!serviceProviderOptional.isPresent()) {
            throw new IllegalArgumentException("ServiceProvider with this id doesn't exist!");
        }
        List<ServiceOffer> serviceOffers = serviceOfferRepository
                .findAllByServiceProvider(serviceProviderOptional.get());
        return serviceOfferMapper.listToListDTO(serviceOffers);
    }

    @Override
    public List<ServiceOfferResponseDTO> findAllPlayroomByBookingDetails(String city, int numberOfKids,
                                                                         int numberOfAdults, LocalDate date, LocalTime startTime, LocalTime endTime) {

        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        List<ServiceOffer> offers = serviceOfferRepository.findAllByBookingDetails(date, startTime, endTime, city,
                numberOfKids, numberOfAdults, TypeOfServiceProvider.PLAYROOM);


        List<ServiceOffer> offersForDelete = serviceOfferRepository.findAllForRemoved(city, date, startTime, endTime);

        List<ServiceOffer> listOffersForDelete = new ArrayList<>();

        for (ServiceOffer serviceOffer : offers) {
            for (ServiceOffer serviceOfferForDelete : offersForDelete) {
                if (serviceOffer.getServiceProvider().getId() == serviceOfferForDelete.getServiceProvider().getId()) {
                    listOffersForDelete.add(serviceOffer);
                }
            }
        }
        offers.removeAll(listOffersForDelete);

//		//prolazi kroz sve ponude koje su aktivne dobijenog datuma
//		for (ServiceOffer serviceOffer : offers) {
//			//prolazi kroz sve rezervacije
//			for (Reservation res : serviceOffer.getReservations()) {
//				if(res.getDateOfReservation().equals(date)) {
//					//ako postoji rezervacija za neku ponudu u istom terminu
//					if(((res.getStartTime().isBefore(startTime) || res.getStartTime().equals(startTime)) && res.getEndTime().isAfter(startTime)) || 
//							(res.getStartTime().isBefore(endTime) && (res.getEndTime().isAfter(endTime) || res.getEndTime().equals(endTime)))) {
//						//prolazi opet kroz sve ponude
//						for (ServiceOffer so : offers) {
//							//pronalazi sve ponude te igraonice i brise iz liste
//							if(so.getServiceProvider() == res.getServiceOffer().getServiceProvider()) {
//								offersForDelete.add(so);
//							}
//						}
//					}
//				}
//				
//			}
//		}
//		
//		offers.removeAll(offersForDelete);
        return serviceOfferMapper.listToListDTO(offers);
    }

    @Override
    public List<ServiceOfferResponseDTO> findAdditionalOffersByPlayroom(UUID playroomOfferId,
                                                                        String additionalOffersType, String city, int numberOfKids, int numberOfAdults, LocalDate date,
                                                                        LocalTime startTime, LocalTime endTime) {

        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        ServiceOffer playroomsOffer = serviceOfferRepository.findById(playroomOfferId).orElseThrow(
                () -> new IllegalArgumentException("Offer with id " + playroomOfferId + " dosen't exist."));

        TypeOfServiceProvider typeOfServiceProvider = TypeOfServiceProvider.valueOf(additionalOffersType);
        List<ServiceOffer> offers = serviceOfferRepository
                .findAllByServiceProviderServicesCooperationsPlayroomIdAndServiceProviderServicesCooperationsConfirmedAndServiceProviderTypeOfServiceProviderAndServiceProviderCityAndServiceProviderMaxNumberOfKidsGreaterThanEqual(
                        playroomsOffer.getServiceProvider().getId(), true, typeOfServiceProvider, city, numberOfKids);

        // if the chosen playroom hasn't cooperation, get all additional offers
        if (offers.isEmpty()) {
            offers = serviceOfferRepository
                    .findAllByServiceProviderTypeOfServiceProviderAndServiceProviderCityAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                            typeOfServiceProvider, city, date, date);
        }
        return serviceOfferMapper.listToListDTO(offers);
    }

}
