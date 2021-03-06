package com.diplomski.bookingkidsparty.app.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ReservationResponseDTO {

	private UUID id;
	private LocalDate dateOfReservation;
	private LocalTime startTime;
	private LocalTime endTime;
	private int numberOfKids;
	private int numberOfAdults;
	private String additionalRequirements;
	private int ageOfKid;
	private long totalPrice;
	private UUID userId;
	private String userEmail;
	private UUID serviceProviderId;
	private String serviceProviderName;
	private String typeOfServiceProvider;
	private String serviceOfferName;
	private UUID playroomId;
	private String playroomName;
	private String playroomAdress;
	private boolean hasRating;
}
