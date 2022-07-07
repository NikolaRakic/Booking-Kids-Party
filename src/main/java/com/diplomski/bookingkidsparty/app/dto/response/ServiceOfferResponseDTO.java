package com.diplomski.bookingkidsparty.app.dto.response;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ServiceOfferResponseDTO {

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
	private String serviceProviderUsername;
	private String serviceProviderAdress;
	private String serviceProviderCity;
	private Set<PhotoResponseDTO> serviceProviderPhotos;
	private Double averageRating;
}
