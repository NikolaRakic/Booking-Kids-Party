package com.diplomski.bookingkidsparty.app.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RatingDTOreq {

	private String comment;
	private int rate;
	private UUID reservationId;
}
