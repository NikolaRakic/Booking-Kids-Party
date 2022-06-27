package com.diplomski.bookingkidsparty.app.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.service.PaymentService;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerSearchParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;

@Service
public class PaymentStripeImpl implements PaymentService{

	@Value("${STRIPE_SECRET_KEY}")
	private String secretKey;
	
    static class CreatePaymentResponse {
        private String clientSecret;
		private String paymentIntentId;
        public CreatePaymentResponse(String clientSecret, String paymentIntentId) {
          this.clientSecret = clientSecret;
          this.paymentIntentId = paymentIntentId;
        }
      }
    
    private static Gson gson = new Gson();
    
	@Override
	public String createPaymentIntent(Long amount, String username, String email) throws StripeException {
		Stripe.apiKey = secretKey;
		
		String customerId = hasCustomer(email);
		if(customerId == null) {
			customerId = createCustomer(email, username);
		}
		PaymentIntentCreateParams params =
		          PaymentIntentCreateParams.builder()
		            .setAmount(amount)
		            .setCurrency("EUR")
		            .setReceiptEmail(email)
		            .setCustomer(customerId)
		            .setAutomaticPaymentMethods(
		              PaymentIntentCreateParams.AutomaticPaymentMethods
		                .builder()
		                .setEnabled(true)
		                .build()
		            )
		            .build();
		
		 PaymentIntent paymentIntent = PaymentIntent.create(params);
	     CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret(), paymentIntent.getId());
	     return gson.toJson(paymentResponse);
	}
	
	private String createCustomer(String email, String username) throws StripeException {
		CustomerCreateParams customerParams =
  			  CustomerCreateParams
  			    .builder()
  			    .setName(username)
  			    .setEmail(email)
  			    .setPaymentMethod("pm_card_visa")
  			    .setInvoiceSettings(
  			      CustomerCreateParams.InvoiceSettings
  			        .builder()
  			        .setDefaultPaymentMethod("pm_card_visa")
  			        .build()
  			    )
  			    .build();

  			Customer customer = Customer.create(customerParams);
  			return customer.getId();
		
	}

	private String hasCustomer(String email) throws StripeException {
		CustomerSearchParams params =
  			  CustomerSearchParams
  			    .builder()
  			    .setQuery("email:'"+email+"'")
  			    .build();

  			CustomerSearchResult result = Customer.search(params);
  			if(!result.getData().isEmpty()) {
  				return result.getData().get(0).getId();
  			}
  			return null;
	}

	@Override
	public void refunde(String paymentIntentId) throws StripeException {
		Stripe.apiKey = secretKey;

		RefundCreateParams params =
		  RefundCreateParams.builder().setPaymentIntent(paymentIntentId).build();

		Refund.create(params);
		
	}

	
}
