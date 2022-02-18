package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.RatingDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.StarRatingDTOres;
import com.diplomski.bookingkidsparty.app.dto.response.RatingDTOres;

public interface RatingService {

	UUID create(RatingDTOreq ratingDto) throws Exception;

	List<RatingDTOres> getAllByServiceProvider(UUID serviceProviderId);

	boolean delete(UUID id);

	StarRatingDTOres getAverageRatingByServiceProvider(UUID serviceProviderId);

}
