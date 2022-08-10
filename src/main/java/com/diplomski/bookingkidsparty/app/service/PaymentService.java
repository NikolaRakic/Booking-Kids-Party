package com.diplomski.bookingkidsparty.app.service;

import com.stripe.exception.StripeException;

public interface PaymentService {

	String createPaymentIntent(double amount, String username, String email) throws StripeException;

	void refunde(String clientSecret) throws StripeException;

}
