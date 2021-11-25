package com.diplomski.bookingkidsparty.app.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReservationDTOreq {

	private LocalDate dateOfReservation;
	
	private LocalTime startTime;
	
	private LocalTime endTime;

	private int numberOfKids;
	
	private int numberOfAdults;
	
	private String additionalRequirements;
	
	private int ageOfKid;
	
	private UUID userId;
	
	private UUID serviceOfferId;
	
	private UUID playroomId;
}
