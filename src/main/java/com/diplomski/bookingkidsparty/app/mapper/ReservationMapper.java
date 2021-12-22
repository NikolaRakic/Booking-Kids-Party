package com.diplomski.bookingkidsparty.app.mapper;

import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.ReservationDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ReservationDTOres;
import com.diplomski.bookingkidsparty.app.model.Reservation;
import com.diplomski.bookingkidsparty.app.model.ServiceOffer;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.repository.ServiceOfferRepository;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.repository.UserRepository;


@Component
public class ReservationMapper {
	
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ServiceOfferRepository serviceOfferRepository;
	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	
	public Reservation dtoToEntity(ReservationDTOreq reservationDTOreq) {
		User user = userRepository.findById(reservationDTOreq.getUserId()).get();
		ServiceProvider playroom = null;
		ServiceOffer serviceOffer = serviceOfferRepository.findById(reservationDTOreq.getServiceOfferId()).get();
		
		if(!serviceOffer.getServiceProvider().getTypeOfServiceProvider().getName().equals("Igraonica")) {
			playroom = serviceProviderRepository.findById(reservationDTOreq.getPlayroomId()).get();
		}
		else {
			playroom = serviceOfferRepository.findById(reservationDTOreq.getServiceOfferId()).get().getServiceProvider();
		}
		
		Reservation reservation = new Reservation();
		
		reservation.setAdditionalRequirements(reservationDTOreq.getAdditionalRequirements());
		reservation.setAgeOfKid(reservationDTOreq.getAgeOfKid());
		reservation.setDateOfReservation(reservationDTOreq.getDateOfReservation());
		reservation.setEndTime(reservationDTOreq.getEndTime());
		reservation.setNumberOfAdults(reservationDTOreq.getNumberOfAdults());
		reservation.setNumberOfKids(reservationDTOreq.getNumberOfKids());
		reservation.setStartTime(reservationDTOreq.getStartTime());
		reservation.setPlayroom(playroom);
		reservation.setServiceOffer(serviceOffer);
		reservation.setUser(user);
		
		return reservation;
		
//		TypeMap<ReservationDTOreq, Reservation> typeMap = modelMapper.getTypeMap(ReservationDTOreq.class, Reservation.class);
//		if(typeMap == null) {
//			User user = userRepository.findById(reservationDTOreq.getUserId()).get();
//			ServiceOffer serviceOffer = serviceOfferRepository.findById(reservationDTOreq.getServiceOfferId()).get();
//				
//			modelMapper.addMappings(new PropertyMap<ReservationDTOreq, Reservation>() {
//	            @Override
//	            protected void configure() {
//	                skip(destination.getId());
//	                map().setUser(user);
//	                map().setServiceOffer(serviceOffer);
//	                
//	            }
//	        });
//		}
//		return modelMapper.map(reservationDTOreq, Reservation.class);
	}
	
	public ReservationDTOres entityToDTO(Reservation reservation) {
		long minutes = MINUTES.between(reservation.getStartTime(), reservation.getEndTime());
		double hours = (double)minutes / 60;
		
		long totalPrice = Math.round(reservation.getNumberOfKids() * reservation.getServiceOffer().getPricePerHourForKid() * hours
		+ reservation.getNumberOfAdults() * reservation.getServiceOffer().getPricePerHourForAdult() * hours);
		modelMapper.addMappings(new PropertyMap<Reservation, ReservationDTOres>() {
            @Override
            protected void configure() {
                map().setTotalPrice(totalPrice);
            }
        });
		return modelMapper.map(reservation, ReservationDTOres.class);
	}
	
	public List<ReservationDTOres> listToListDTO(List<Reservation> reservations){
		List<ReservationDTOres> reservationsDTO = new ArrayList<ReservationDTOres>();
		for (Reservation reservation : reservations) {
			reservationsDTO.add(entityToDTO(reservation));
		}
		return reservationsDTO;
	}

}
