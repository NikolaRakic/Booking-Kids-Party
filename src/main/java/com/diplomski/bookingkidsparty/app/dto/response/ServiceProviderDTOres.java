package com.diplomski.bookingkidsparty.app.dto.response;

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
	private String password;
	private String pib;
	private int maxNumberOfKids;
	private UUID typeOfServiceProviderId;
	private String city;
	private String adress;
	private String telephoneNumber;
}
