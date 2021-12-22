package com.diplomski.bookingkidsparty.app.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.RatingDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.RatingDTOres;
import com.diplomski.bookingkidsparty.app.model.Rating;
import com.diplomski.bookingkidsparty.app.model.Reservation;
import com.diplomski.bookingkidsparty.app.repository.ReservationRepository;

@Component
public class RatingMapper {

	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	ModelMapper modelMapper;
	
	public Rating dtoToEntity(RatingDTOreq ratingDto) {
		TypeMap<RatingDTOreq, Rating> typeMap = modelMapper.getTypeMap(RatingDTOreq.class, Rating.class);
		if(typeMap == null) {
			Optional<Reservation> reservationForRatingOptional = reservationRepository.findById(ratingDto.getReservationId());
			if(reservationForRatingOptional.isPresent()) {
				Reservation reservation = reservationForRatingOptional.get();
				modelMapper.addMappings(new PropertyMap<RatingDTOreq, Rating>() {
				@Override
				protected void configure() {
					skip(destination.getId());
					map().setDateTime(LocalDateTime.now());
					map().setReservation(reservation);
				}	            
		    });
			}
		}
		return modelMapper.map(ratingDto, Rating.class); 
	}
	
	public RatingDTOres entityToDto(Rating rating) {
		return modelMapper.map(rating, RatingDTOres.class);
	}
	
	public List<RatingDTOres> listToListDTO(List<Rating> ratings){
		List<RatingDTOres> ratingsDto = new ArrayList<>();
		for (Rating rating : ratings) {
			ratingsDto.add(entityToDto(rating));
		}
		return ratingsDto;
	}
	
}
