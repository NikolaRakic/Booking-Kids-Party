package com.diplomski.bookingkidsparty.app.dto.request;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceProviderEditDTO {

	private String username;
	private String accountNumber;
	private String password;
	private LocalTime startOfWork;
	private LocalTime endOfWork;
	private int maxNumberOfKids;
	private String typeOfServiceProvider;
	private String city;
	private String adress;
	private String telephoneNumber;

}
