package com.diplomski.bookingkidsparty.app.service;

import com.stripe.exception.StripeException;

public interface PaymentService {

	String createPaymentIntent(Long amount, String username, String email) throws StripeException;

}
