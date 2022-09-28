package com.diplomski.bookingkidsparty.app.mapper;

import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.ReservationRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.request.ServiceOfferRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ReservationResponseDTO;
import com.diplomski.bookingkidsparty.app.model.Rating;
import com.diplomski.bookingkidsparty.app.model.Reservation;
import com.diplomski.bookingkidsparty.app.model.ServiceOffer;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.model.enums.Role;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.RatingRepository;
import com.diplomski.bookingkidsparty.app.repository.ServiceOfferRepository;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.repository.UserRepository;
import com.diplomski.bookingkidsparty.app.util.Price;

import javassist.NotFoundException;

@Component
@RequiredArgsConstructor
public class ReservationMapper {

	private final UserRepository userRepository;

	private final ServiceOfferRepository serviceOfferRepository;

	private final RatingRepository ratingRepository;

	public List<Reservation> dtoToEntity(ReservationRequestDTO reservationDTOreq) {
		Optional<User> userOptional = userRepository.findByUsernameOrEmail(reservationDTOreq.getUsersEmail());
		User user;
		if (!userOptional.isPresent()) {
			user = new User().withUserRole(Role.ROLE_UNREGISTERED);
			userRepository.saveAndFlush(user);
		} else {
			user = userOptional.get();
		}

		ServiceProvider playroom = null;
		Set<ServiceOffer> serviceOffers = new HashSet<ServiceOffer>();

		for (UUID serviceOfferId : reservationDTOreq.getServiceOffers()) {
			ServiceOffer serviceOffer = serviceOfferRepository.findById(serviceOfferId).orElseThrow(
					() -> new IllegalArgumentException("ServiceOffer with id " + serviceOfferId + " doesn't exist."));
			if (serviceOffer.getServiceProvider().getTypeOfServiceProvider() == TypeOfServiceProvider.PLAYROOM)
				playroom = serviceOffer.getServiceProvider();
			serviceOffers.add(serviceOffer);
		}
		
		if(playroom == null) {
			throw new IllegalArgumentException("Reservation must have Playroom");
		}

		List<Reservation> reservations = new ArrayList<Reservation>();

		for (ServiceOffer serviceOffer : serviceOffers) {
			reservations.add(createReservation(reservationDTOreq, serviceOffer, playroom, user));
		}
		return reservations;

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

	private Reservation createReservation(ReservationRequestDTO reservationDTOreq, ServiceOffer serviceOffer,
			ServiceProvider playroom, User user) {

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
	}

	public ReservationResponseDTO entityToDTO(Reservation reservation) {
		// TypeMap<Reservation, ReservationDTOres> typeMap =
		// modelMapper.getTypeMap(Reservation.class, ReservationDTOres.class);

		Rating rating = ratingRepository.findByReservationId(reservation.getId()).orElse(null);
		
		
		long totalPrice = Price.getTotalPrice(reservation);

		ReservationResponseDTO reservationDto = new ReservationResponseDTO();

		reservationDto.setAdditionalRequirements(reservation.getAdditionalRequirements());
		reservationDto.setAgeOfKid(reservation.getAgeOfKid());
		reservationDto.setDateOfReservation(reservation.getDateOfReservation());
		reservationDto.setEndTime(reservation.getEndTime());
		reservationDto.setId(reservation.getId());
		reservationDto.setNumberOfAdults(reservation.getNumberOfAdults());
		reservationDto.setNumberOfKids(reservation.getNumberOfKids());
		reservationDto.setPlayroomId(reservation.getPlayroom().getId());
		reservationDto.setPlayroomName(reservation.getPlayroom().getUsername());
		reservationDto.setPlayroomAdress(reservation.getPlayroom().getAdress());
		reservationDto.setServiceProviderName(reservation.getServiceOffer().getServiceProvider().getUsername());
		reservationDto.setServiceProviderId(reservation.getServiceOffer().getServiceProvider().getId());
		reservationDto.setServiceOfferName(reservation.getServiceOffer().getName());
		reservationDto.setStartTime(reservation.getStartTime());
		reservationDto.setUserId(reservation.getUser().getId());
		reservationDto.setUserEmail(reservation.getUser().getEmail());
		reservationDto.setTypeOfServiceProvider(reservation.getServiceOffer().getServiceProvider().getTypeOfServiceProvider().name());
		reservationDto.setTotalPrice(totalPrice);
		if(rating != null) {
			reservationDto.setHasRating(true);
		}
		else {
			reservationDto.setHasRating(false);
		}

		return reservationDto;
//			if(typeMap == null) {
//			modelMapper.addMappings(new PropertyMap<Reservation, ReservationDTOres>() {
//	            @Override
//	            protected void configure() {
//	                map().setTotalPrice(totalPriceFinal);
//	            }
//	        });
//		}
//		
//		return modelMapper.map(reservation, ReservationDTOres.class);
	}

	public List<ReservationResponseDTO> listToListDTO(List<Reservation> reservations) {
		List<ReservationResponseDTO> reservationsDTO = new ArrayList<ReservationResponseDTO>();
		for (Reservation reservation : reservations) {
			reservationsDTO.add(entityToDTO(reservation));
		}
		return reservationsDTO;
	}

}
