package com.diplomski.bookingkidsparty.app.service.impl;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.service.EmailSenderService;


@Service
public class EmailSenderServiceImpl implements EmailSenderService{
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Override
	public void sendMail(String toEmail, String plainPassword) throws MessagingException{
		
		MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(toEmail);

        helper.setSubject("Booking kids parties");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<b>Vaš nalog je kreiran!<b> <br/> <h1>Vaša lozinka je: <b>"+ plainPassword +"</b></h1>", true);

        //helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);
	}

}
