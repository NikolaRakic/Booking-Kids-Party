package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.Tuple;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.RatingRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.StarRatingResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.RatingResponseDTO;
import com.diplomski.bookingkidsparty.app.mapper.RatingMapper;
import com.diplomski.bookingkidsparty.app.model.Rating;
import com.diplomski.bookingkidsparty.app.repository.RatingRepository;
import com.diplomski.bookingkidsparty.app.repository.ReservationRepository;
import com.diplomski.bookingkidsparty.app.service.RatingService;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

	private final RatingRepository ratingRespository;
	private final RatingMapper ratingMapper;
	
	@Override
	public RatingResponseDTO create(RatingRequestDTO ratingDto){
		if(ratingDto.getRate() < 0 || ratingDto.getRate() > 5) {
			throw new IllegalArgumentException("Rate must be between 0 and 5!");
		}
		if(ratingRespository.findByReservationId(ratingDto.getReservationId()).isPresent()) {
			throw new IllegalArgumentException("Rating for this reservation arleady exists");
		}
		Rating rating = ratingMapper.dtoToEntity(ratingDto);
		return ratingMapper.entityToDto(ratingRespository.saveAndFlush(rating));
	}

	@Override
	public List<RatingResponseDTO> getAllByServiceProvider(UUID serviceProviderId) {
		List<Rating> ratings = ratingRespository.findAllByReservationServiceOfferServiceProviderId(serviceProviderId);
		return ratingMapper.listToListDTO(ratings);
	}

	@Override
	public boolean delete(UUID id) {
		Optional<Rating> ratingOprional = ratingRespository.findById(id);
		if(ratingOprional.isPresent()) {
			ratingRespository.delete(ratingOprional.get());
			return true;
		}
		return false;
	}

	@Override
	public StarRatingResponseDTO getAverageRatingByServiceProvider(UUID serviceProviderId) {
		List<Tuple> avg = ratingRespository.avg(serviceProviderId);
		StarRatingResponseDTO averageRatingDTOres = new StarRatingResponseDTO(0,0);
		
			for (Tuple long1 : avg) {
				averageRatingDTOres.setCountOfRate( (long) long1.get(0));
				if(long1.get(1) != null) {
					averageRatingDTOres.setAverageRating( (double) long1.get(1));
				}
			}
		
		return averageRatingDTOres;
	}

}
