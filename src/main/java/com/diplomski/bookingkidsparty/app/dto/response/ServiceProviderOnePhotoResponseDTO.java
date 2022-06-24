package com.diplomski.bookingkidsparty.app.dto.response;

import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ServiceProviderOnePhotoResponseDTO {

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
	private PhotoResponseDTO photo;
}
