package com.diplomski.bookingkidsparty.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diplomski.bookingkidsparty.app.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;


@RestController
@RequestMapping("/payment1")
public class PaymentController {
	

	
	@Autowired
	PaymentService paymentService;
	
	 @PostMapping
	    public String test( 
				 @RequestHeader(value="amount") Long amount,
				 @RequestHeader(value="username") String username,
				 @RequestHeader(value="email") String email) throws StripeException {
		 
		 return paymentService.createPaymentIntent(amount, username, email);
		 
	 }

}
