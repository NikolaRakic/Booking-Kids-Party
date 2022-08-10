package com.diplomski.bookingkidsparty.app.service;

import java.util.List;

import javax.mail.MessagingException;

import com.diplomski.bookingkidsparty.app.model.Reservation;

public interface EmailSenderService {

	void sendPasswordOnMail(String toEmail, String plainPassword);
	
	void sendConfirmReservationOnMail(String toEmail, Reservation playroomReservation, List<Reservation> additionalReservations);
}
