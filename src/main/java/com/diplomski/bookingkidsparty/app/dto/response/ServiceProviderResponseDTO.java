package com.diplomski.bookingkidsparty.app.dto.response;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ServiceProviderResponseDTO {

	private UUID id;
	private String username;
	private String accountNumber;
	private String email;
	private String pib;
	private LocalTime startOfWork;
	private LocalTime endOfWork;
	private int maxNumberOfKids;
	private String typeOfServiceProvider;
	private String city;
	private String adress;
	private String telephoneNumber;
	//private List<PhotoDTOres> photos;
}
