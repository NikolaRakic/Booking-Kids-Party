package com.diplomski.bookingkidsparty.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StarRatingResponseDTO {

	private double averageRating;
	
	private long countOfRate;
	
}
