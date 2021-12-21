package com.diplomski.bookingkidsparty.app.dto.response;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.model.Photo;

import lombok.Data;

@Data
public class ServiceOfferDTOres {

	private UUID id;
	private LocalDate startDate;
	private LocalDate endDate;
	private int maxNumberOfKids;
	private int maxNumberOfAdults;
	private int pricePerHourForKid;
	private int pricePerHourForAdult;
	private String name;
	private UUID serviceProviderId;
	private String serviceProviderName;
	private String serviceProviderAdress;
	private Set<Photo> serviceProviderPhotos;
}
