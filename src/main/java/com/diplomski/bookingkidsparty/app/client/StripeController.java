package com.diplomski.bookingkidsparty.app.client;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;

@RestController
@RequestMapping("/payment")
public class StripeController {
	
    private StripeClient stripeClient;

    @Autowired
    StripeController(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

//    @PostMapping
//    public String chargeCard(@RequestHeader(value="token") String token, 
//    						 @RequestHeader(value="amount") Double amount,
//    						 @RequestHeader(value="email") String email) throws Exception {
//        System.out.println(token);
//        System.out.println(amount);
//        return this.stripeClient.chargeNewCard(token, amount, email).toJson();
//
//    }
    

    @Value("${STRIPE_SECRET_KEY}")
	private String secretKey;
    
    static class CreatePaymentResponse {
        private String clientSecret;
        public CreatePaymentResponse(String clientSecret) {
          this.clientSecret = clientSecret;
        }
      }
    private static Gson gson = new Gson();
    
    @PostMapping
    public String test( 
			 @RequestHeader(value="amount") Long amount,
			 @RequestHeader(value="username") String username,
			 @RequestHeader(value="email") String email) throws StripeException {
    	System.out.println("username email: " + username + " " + email);
    	Stripe.apiKey = "sk_test_51L96moCadxqwLTZTMyoZNqstmJ1XG8BHXqO2PUB0LhE12NFUrSLSbZU2GFxxnVkFMU5fNHbN9LfhVkuww0furSFB00zOW7bBNC";
    	
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
    			
        PaymentIntentCreateParams params =
          PaymentIntentCreateParams.builder()
            .setAmount(amount)
            .setCurrency("EUR")
//            .setReceiptEmail(email)
            .setCustomer(customer.getId())
            .setAutomaticPaymentMethods(
              PaymentIntentCreateParams.AutomaticPaymentMethods
                .builder()
                .setEnabled(true)
                .build()
            )
            .build();

        // Create a PaymentIntent with the order amount and currency
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());
        return gson.toJson(paymentResponse);
    }
}



