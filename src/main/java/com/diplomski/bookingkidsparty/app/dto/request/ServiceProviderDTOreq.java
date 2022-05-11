package com.diplomski.bookingkidsparty.app.dto.request;

import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ServiceProviderDTOreq {

	private String username;
	private String accountNumber;
	private String email;
	private String password;
	private String pib;
	private LocalTime startOfWork;
	private LocalTime endOfWork;
	private int maxNumberOfKids;
	private String typeOfServiceProvider;
	private String city;
	private String adress;
	private String telephoneNumber;
}
