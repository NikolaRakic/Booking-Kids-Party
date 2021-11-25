package com.diplomski.bookingkidsparty.app.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReservationDTOres {

	private UUID id;
	
	private LocalDate dateOfReservation;
	
	private LocalTime startTime;
	
	private LocalTime endTime;

	private int numberOfKids;
	
	private int numberOfAdults;
	
	private String additionalRequirements;
	
	private int ageOfKid;
	
	private UUID userId;
	
	private String userUserName;
	
	private UUID serviceOfferId;
	
	private String serviceOfferName;
	
	private UUID playroomId;
	
	private String playroomName;
}
