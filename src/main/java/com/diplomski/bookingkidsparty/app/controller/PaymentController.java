package com.diplomski.bookingkidsparty.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diplomski.bookingkidsparty.app.service.PaymentService;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	PaymentService paymentService;

	@PostMapping("/create")
	public String createPayment(@RequestHeader(value = "amount") float amount, @RequestHeader(value = "username") String username,
			@RequestHeader(value = "email") String email) throws StripeException {
		Long amountLong = (long) amount;
		return paymentService.createPaymentIntent(amountLong, username, email);
	}
	
	@PostMapping("/refund")
	public void refunde(@RequestHeader(value = "clientSecret") String clientSecret) throws StripeException {
		System.out.println("refundacija za: " + clientSecret);
		paymentService.refunde(clientSecret);
	}

}
