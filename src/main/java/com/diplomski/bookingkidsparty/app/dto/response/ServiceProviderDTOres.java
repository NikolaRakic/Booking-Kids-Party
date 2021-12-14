package com.diplomski.bookingkidsparty.app.dto.response;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ServiceProviderDTOres {

	private UUID id;
	private String name;
	private String accountNumber;
	private String email;
	private String pib;
	private LocalTime startOfWork;
	private LocalTime endOfWork;
	private int maxNumberOfKids;
	private String typeOfServiceProviderName;
	private String city;
	private String adress;
	private String telephoneNumber;
	private Set<PhotoDTOres> photos;
}
