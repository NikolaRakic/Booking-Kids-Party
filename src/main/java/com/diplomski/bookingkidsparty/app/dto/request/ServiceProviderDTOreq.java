package com.diplomski.bookingkidsparty.app.dto.request;

import java.time.LocalTime;
import java.util.UUID;

import lombok.Data;

@Data
public class ServiceProviderDTOreq {

	private String name;
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
