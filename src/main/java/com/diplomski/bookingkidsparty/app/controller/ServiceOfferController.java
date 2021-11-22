package com.diplomski.bookingkidsparty.app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceOfferDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceOfferDTOres;
import com.diplomski.bookingkidsparty.app.service.ServiceOfferService;

import javassist.NotFoundException;

@Controller
public class ServiceOfferController {
	
	@Autowired
	ServiceOfferService serviecOfferService;
	
	@GetMapping("/serviceOffer")
	public ResponseEntity<List<ServiceOfferDTOres>> findAll(){
		List<ServiceOfferDTOres> serviceOffersDto = serviecOfferService.findAll();
		return new ResponseEntity<List<ServiceOfferDTOres>>(serviceOffersDto, HttpStatus.OK);
	}
	
	@GetMapping("/serviceOffer/{id}")
	public ResponseEntity<ServiceOfferDTOres> findOne(@PathVariable("id") UUID id) throws Exception{
		ServiceOfferDTOres serviceOfferDto = serviecOfferService.findOne(id);
		return new ResponseEntity<ServiceOfferDTOres>(serviceOfferDto, HttpStatus.OK);
	}

	@PostMapping("/serviceOffer")
	public ResponseEntity<UUID> add(@RequestBody ServiceOfferDTOreq serviceOfferDTOreq) {
		UUID id = serviecOfferService.add(serviceOfferDTOreq);
		return new ResponseEntity<UUID>(id, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/serviceOffer/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id) throws Exception{
		HttpStatus status = serviecOfferService.delete(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(status);
	}
	
	@PutMapping("/serviceOffer/{id}")
	public ResponseEntity<?> edit(@PathVariable("id") UUID id, @RequestBody ServiceOfferDTOreq serviceOfferDTO){
		try {
			serviecOfferService.edit(id, serviceOfferDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);	
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);	
		}
	}
	
}
