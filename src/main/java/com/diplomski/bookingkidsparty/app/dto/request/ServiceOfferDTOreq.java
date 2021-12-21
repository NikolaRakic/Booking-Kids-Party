package com.diplomski.bookingkidsparty.app.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class ServiceOfferDTOreq {

	private LocalDate startDate;
	private LocalDate endDate;
	private int maxNumberOfKids;
	private int maxNumberOfAdults;
	private int pricePerHourForKid;
	private int pricePerHourForAdult;
	private String name;
	private UUID serviceProviderId;
	
}
