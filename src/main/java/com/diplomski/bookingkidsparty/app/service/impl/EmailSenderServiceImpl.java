package com.diplomski.bookingkidsparty.app.service.impl;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.model.Reservation;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.service.EmailSenderService;
import com.diplomski.bookingkidsparty.app.util.Price;


@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService{

    private final JavaMailSender javaMailSender;
	
	@Override
	public void sendPasswordOnMail(String toEmail, String plainPassword){
		
		MimeMessage msg = javaMailSender.createMimeMessage();

		try{
			// true = multipart message
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			helper.setTo(toEmail);

			helper.setSubject("Booking kids parties");

			// default = text/plain
			//helper.setText("Check attachment for image!");

			// true = text/html
			helper.setText("<b>Vaš nalog je kreiran!<b> <br/> <h1>Vaša lozinka je: <b>"+ plainPassword +"</b></h1>", true);

			//helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));
		}catch (MessagingException ex){
			new MessagingException();
		}


        javaMailSender.send(msg);
	}

	@Override
	public void sendConfirmReservationOnMail(String toEmail, Reservation playroomReservation, List<Reservation> additionalReservations) {
		long minutes = MINUTES.between(playroomReservation.getStartTime(), playroomReservation.getEndTime());
		
		String addiotalReservationsTable = "";
		
		 for(Reservation res : additionalReservations) {
 			addiotalReservationsTable += " <br/></br> <table style=\"font-family: Arial, Helvetica, sans-serif;\r\n" + 
 					"  border-collapse: collapse;\r\n" + 
 					"  width: 60%; \">"
 					+ "<tr><td>"+res.getServiceOffer().getServiceProvider().getTypeOfServiceProvider()+"</td></tr>"
 					+ "<tr><th style=\"border: 1px solid #ddd;\r\n" + 
 					"  padding: 8px;\">Naziv</th>"
 					+ "<td style=\"border: 1px solid #ddd; padding: 8px;\" ><b>"+ res.getServiceOffer().getServiceProvider().getUsername()+"</b></td>"
 					+ "<tr><th style=\"border: 1px solid #ddd; padding: 8px;\">Email</th>"
 					+ "<td><b>"+ res.getServiceOffer().getServiceProvider().getEmail()+"</b></td></tr>"
 					+ "<tr><th style=\"border: 1px solid #ddd; padding: 8px;\">Ime usluge</th>"
 					+ "<td style=\"border: 1px solid #ddd; padding: 8px;\"><b>"+ res.getServiceOffer().getName()+"</b></td></tr>"
 							+ "<tr><th style=\"border: 1px solid #ddd; padding: 8px;\">Cena</th>"
 		 					+ "<td style=\"border: 1px solid #ddd; padding: 8px;\"><b>"+ Price.getTotalPrice(res)+"din</b></td></tr>"
 					+ "</table> <br/></br>";
 		}
		
		
		MimeMessage msg = javaMailSender.createMimeMessage();

		 try{
			 MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			 helper.setTo(toEmail);
			 helper.setSubject("Booking kids parties");

			 helper.setText("<b>Uspešna rezervacija!<b> <br/><br/><br/> "
							 + "<table style=\"font-family: Arial, Helvetica, sans-serif;\r\n" +
							 "  border-collapse: collapse;\r\n" +
							 "  width: 60%;\">"
							 + "<tr><td>IGRAONICA</td></tr>"

							 + "<tr><th style=\"border: 1px solid #ddd; padding: 8px;\">Naziv</th>"
							 + "<td style=\"border: 1px solid #ddd; padding: 8px;\"><b>"+ playroomReservation.getServiceOffer().getServiceProvider().getUsername()+"</b><td></tr>"

							 + "<tr><th style=\"border: 1px solid #ddd; padding: 8px;\">Naziv usluge</th>"
							 + "<td style=\"border: 1px solid #ddd; padding: 8px;\"><b>"+ playroomReservation.getServiceOffer().getName()+"</b><td></tr>"


							 + "<tr><th style=\"border: 1px solid #ddd; padding: 8px;\">Broj dece</th>"
							 + "<td style=\"border: 1px solid #ddd; padding: 8px;\"><b>"+ playroomReservation.getNumberOfKids()+"</b><td></tr>"
							 + "<tr><th style=\"border: 1px solid #ddd; padding: 8px;\">Broj odraslih</th>"
							 + "<td style=\"border: 1px solid #ddd; padding: 8px;\"><b>"+ playroomReservation.getNumberOfAdults()+"</b><td></tr>"
							 + "<tr><th style=\"border: 1px solid #ddd; padding: 8px;\">Datum proslave</th>"
							 + "<td style=\"border: 1px solid #ddd; padding: 8px;\"><b>"+ playroomReservation.getDateOfReservation()+"</b><td></tr>"
							 + "<tr><th style=\"border: 1px solid #ddd; padding: 8px;\">Početak</th>"
							 + "<td style=\"border: 1px solid #ddd; padding: 8px;\"><b>"+ playroomReservation.getStartTime()+"</b><td></tr>"
							 + "<tr><th style=\"border: 1px solid #ddd; padding: 8px;\">Kraj</th>"
							 + "<td style=\"border: 1px solid #ddd; padding: 8px;\"><b>"+ playroomReservation.getEndTime()+"</b><td></tr>"
							 + "<tr><th style=\"border: 1px solid #ddd; padding: 8px;\">Mesto održavanja</th>"
							 + "<td style=\"border: 1px solid #ddd; padding: 8px;\"><b>"+ playroomReservation.getPlayroom().getAdress()+", " + playroomReservation.getServiceOffer().getServiceProvider().getCity()+"</b><td></tr>"
							 + "<tr><th style=\"border: 1px solid #ddd; padding: 8px;\">Cena</th>"
							 + "<td style=\"border: 1px solid #ddd; padding: 8px;\"><b>"+ Price.getTotalPrice(playroomReservation) +" din</b><td></tr>"
							 + "</table"
							 + addiotalReservationsTable
					 , true);
		 } catch (MessagingException ex){
		 		new MessagingException();
		 }

        javaMailSender.send(msg);

	}
	

}
