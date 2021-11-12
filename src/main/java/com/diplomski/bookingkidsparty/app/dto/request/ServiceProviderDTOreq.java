package com.diplomski.bookingkidsparty.app.dto.request;

import java.util.UUID;

import lombok.Data;

@Data
public class ServiceProviderDTOreq {

	private String name;
	private String accountNumber;
	private String email;
	private String password;
	private String pib;
	private int maxNumberOfKids;
	private UUID typeOfServiceProviderId;
	private String city;
	private String adress;
	private String telephoneNumber;
}
