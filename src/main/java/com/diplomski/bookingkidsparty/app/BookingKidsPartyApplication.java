package com.diplomski.bookingkidsparty.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
            title = "Booking Kids Party Application",
            version = "1.0",
            description = "Spring boot application for booking playroom for kids party",
            license = @License(name = "FTN", url = "ftn.rs"),
            contact = @Contact(url = "http://localhost:3000", name = "Nikola Rakic", email = "bookingkidsparties@gmail.com")))
public class BookingKidsPartyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingKidsPartyApplication.class, args);
	}

}
