package com.diplomski.bookingkidsparty.app.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.RatingRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.RatingResponseDTO;
import com.diplomski.bookingkidsparty.app.model.Rating;
import com.diplomski.bookingkidsparty.app.model.Reservation;
import com.diplomski.bookingkidsparty.app.repository.ReservationRepository;

@Component
@RequiredArgsConstructor
public class RatingMapper {

	private final ReservationRepository reservationRepository;

	private final ModelMapper modelMapper;
	
	public Rating dtoToEntity(RatingRequestDTO ratingDto) {
		TypeMap<RatingRequestDTO, Rating> typeMap = modelMapper.getTypeMap(RatingRequestDTO.class, Rating.class);
		if(typeMap == null) {
			Optional<Reservation> reservationForRatingOptional = reservationRepository.findById(ratingDto.getReservationId());
			if(reservationForRatingOptional.isPresent()) {
				Reservation reservation = reservationForRatingOptional.get();
				modelMapper.addMappings(new PropertyMap<RatingRequestDTO, Rating>() {
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
	
	public RatingResponseDTO entityToDto(Rating rating) {
		return modelMapper.map(rating, RatingResponseDTO.class);
	}
	
	public List<RatingResponseDTO> listToListDTO(List<Rating> ratings){
		List<RatingResponseDTO> ratingsDto = new ArrayList<>();
		for (Rating rating : ratings) {
			ratingsDto.add(entityToDto(rating));
		}
		return ratingsDto;
	}
	
}
