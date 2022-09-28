package com.diplomski.bookingkidsparty.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diplomski.bookingkidsparty.app.service.PaymentService;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/create")
	public String createPayment(@RequestHeader(value = "amount") double amount, @RequestHeader(value = "username") String username,
			@RequestHeader(value = "email") String email) throws StripeException {
		return paymentService.createPaymentIntent(amount, username, email);
	}

	@PostMapping("/refund")
	public ResponseEntity<?> refunde(@RequestHeader(value = "clientSecret") String clientSecret) throws StripeException {
		paymentService.refunde(clientSecret);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}
