package com.diplomski.bookingkidsparty.app.util;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.model.ServiceOffer;

public class OfferValidate {

	public static void dateValidate(ServiceOffer serviceOffer) {
		if(serviceOffer.getStartDate().isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("Start date must be in future");
		}
		if(serviceOffer.getEndDate().isBefore(serviceOffer.getStartDate())) {
			throw new IllegalArgumentException("End date must be after start date");
		}
	}
	
}
