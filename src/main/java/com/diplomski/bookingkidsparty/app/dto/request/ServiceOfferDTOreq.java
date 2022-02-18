package com.diplomski.bookingkidsparty.app.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;

import lombok.Data;

@Data
public class ServiceOfferDTOreq {

	private LocalDate startDate;
	private LocalDate endDate;
	private int maxNumberOfKids;
	private int maxNumberOfAdults;
	private int pricePerKid;
	private int pricePerAdult;
	private int pricePerHour;
	private String description;
	private String name;
	private UUID serviceProviderId;
	
}
