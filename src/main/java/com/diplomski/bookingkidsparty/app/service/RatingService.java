package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.RatingRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.StarRatingResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.RatingResponseDTO;

public interface RatingService {

	UUID create(RatingRequestDTO ratingDto) throws Exception;

	List<RatingResponseDTO> getAllByServiceProvider(UUID serviceProviderId);

	boolean delete(UUID id);

	StarRatingResponseDTO getAverageRatingByServiceProvider(UUID serviceProviderId);

}
