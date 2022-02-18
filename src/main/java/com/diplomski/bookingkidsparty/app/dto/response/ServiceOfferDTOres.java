package com.diplomski.bookingkidsparty.app.dto.response;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import lombok.Data;

@Data
public class ServiceOfferDTOres {

	private UUID id;
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
	private String serviceProviderName;
	private String serviceProviderAdress;
	private String serviceProviderCity;
	private Set<PhotoDTOres> serviceProviderPhotos;
}
