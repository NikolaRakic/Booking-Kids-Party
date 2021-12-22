package com.diplomski.bookingkidsparty.app.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RatingDTOres {

	private UUID id;
	
	private LocalDateTime dateTime;
	
	private String comment;
	
	private int rate;
	
	private UUID reservationId;

}
