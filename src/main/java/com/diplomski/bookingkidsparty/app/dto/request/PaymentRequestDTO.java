package com.diplomski.bookingkidsparty.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PaymentRequestDTO {

	private double amount;
	private String currency;
	private String tokenId;
}
