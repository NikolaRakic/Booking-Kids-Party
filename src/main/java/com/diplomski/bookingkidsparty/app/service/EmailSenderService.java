package com.diplomski.bookingkidsparty.app.service;

import javax.mail.MessagingException;

public interface EmailSenderService {

	void sendMail(String toEmail, String plainPassword) throws MessagingException;
}
