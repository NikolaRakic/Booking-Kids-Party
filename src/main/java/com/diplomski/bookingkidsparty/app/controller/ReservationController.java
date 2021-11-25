package com.diplomski.bookingkidsparty.app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.diplomski.bookingkidsparty.app.dto.request.ReservationDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ReservationDTOres;
import com.diplomski.bookingkidsparty.app.service.ReservationService;

@Controller
public class ReservationController {

	@Autowired
	ReservationService reservationService;
	
	@PostMapping("/reservation")
	public ResponseEntity<?> add(@RequestBody ReservationDTOreq reservationDTOreq){
		try {
			UUID id = reservationService.add(reservationDTOreq);
			return new ResponseEntity<UUID>(id, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/reservation")
	public ResponseEntity<List<ReservationDTOres>> getAll(){
		List<ReservationDTOres> reservationsDTOres = reservationService.getAll();
		return new ResponseEntity<List<ReservationDTOres>>(reservationsDTOres, HttpStatus.OK);
	}
}
