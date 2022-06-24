package com.diplomski.bookingkidsparty.app.client;

import org.springframework.stereotype.Component;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.net.ApiResource;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.CustomerSearchParams;

import org.hibernate.query.criteria.internal.predicate.IsEmptyPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Component
public class StripeClient {

	@Value("${STRIPE_SECRET_KEY}")
	private String secretKey;
	
	@Autowired
    StripeClient() {
		System.out.println("secret key: " + secretKey);
        Stripe.apiKey = "sk_test_51L96moCadxqwLTZTMyoZNqstmJ1XG8BHXqO2PUB0LhE12NFUrSLSbZU2GFxxnVkFMU5fNHbN9LfhVkuww0furSFB00zOW7bBNC";
    }
	
	public void createAccount(String email) throws StripeException {
		AccountCreateParams params =
				  AccountCreateParams
				    .builder()
				    .setType(AccountCreateParams.Type.EXPRESS)
				    .setEmail(email)
				    .build();

				Account account = Account.create(params);
			
	}
	
	 public Customer createCustomer(String email) throws Exception {
	        Map<String, Object> customerParams = new HashMap<String, Object>();
	        customerParams.put("email", email);
	        //customerParams.put("source", token);
	        return Customer.create(customerParams);
	    }

	    private boolean isCustomerExists(String email) throws Exception {
	    	CustomerSearchParams params =
	    			  CustomerSearchParams
	    			    .builder()
	    			    .setQuery("email:'"+email+"'")
	    			    .build();

	    			CustomerSearchResult result = Customer.search(params);
	    			if(!result.getData().isEmpty()) {
	    				return true;
	    			}
	    			return false;
	    }

	    public Charge chargeNewCard(String token, double amount, String email) throws Exception {
	    	if(!isCustomerExists(email)) {
	    		createCustomer(email);
	    	}
	  
	        Map<String, Object> chargeParams = new HashMap<String, Object>();
	        chargeParams.put("amount", (int)(amount * 100));
	        chargeParams.put("currency", "RSD");
	        chargeParams.put("source", token);
	        Charge charge = Charge.create(chargeParams);

	        return charge;
	    }

//	    public Charge chargeCustomerCard(String customerId, int amount) throws Exception {
//	        String sourceCard = getCustomer(customerId).getDefaultSource();
//	        Map<String, Object> chargeParams = new HashMap<String, Object>();
//	        chargeParams.put("amount", amount);
//	        chargeParams.put("currency", "RSD");
//	        chargeParams.put("customer", customerId);
//	        chargeParams.put("source", sourceCard);
//	        Charge charge = Charge.create(chargeParams);
//	        return charge;
//	    }
    
}
