package com.diplomski.bookingkidsparty.app.controller;

import java.time.LocalDate;
import java.time.LocalTime;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceOfferDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceOfferDTOres;
import com.diplomski.bookingkidsparty.app.service.ServiceOfferService;

import javassist.NotFoundException;

@RestController
@RequestMapping("serviceOffers")
public class ServiceOfferController {
	
	@Autowired
	ServiceOfferService serviecOfferService;
	
	@GetMapping
	public ResponseEntity<List<ServiceOfferDTOres>> findAll(){
		List<ServiceOfferDTOres> serviceOffersDto = serviecOfferService.findAll();
		return new ResponseEntity<List<ServiceOfferDTOres>>(serviceOffersDto, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") UUID id) throws Exception{
		try {
			ServiceOfferDTOres serviceOfferDto = serviecOfferService.findById(id);
			return new ResponseEntity<ServiceOfferDTOres>(serviceOfferDto, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);	
		}			
	}

	@PostMapping
	public ResponseEntity<UUID> add(@RequestBody ServiceOfferDTOreq serviceOfferDTOreq) throws Exception {
		UUID id = serviecOfferService.add(serviceOfferDTOreq);
		return new ResponseEntity<UUID>(id, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id) throws Exception{
		return new ResponseEntity<>(serviecOfferService.delete(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/{id}")
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
	
	@GetMapping("/serviceProvider/{id}")
	public ResponseEntity<?> findAllByServiceProvider(@PathVariable("id") UUID id){
		List<ServiceOfferDTOres> serviceOffersDto;
		try {
			serviceOffersDto = serviecOfferService.findAllByServiceProvider(id);
			return new ResponseEntity<List<ServiceOfferDTOres>>(serviceOffersDto, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
	}
	
	@GetMapping("/bookingDetails")
	public ResponseEntity<?> findAllByBookingDetails(@RequestParam(value="city", required=true) String city,
			@RequestParam(value="numberOfKids", required=true) int numberOfKids,
			@RequestParam(value="numberOfAdults", required=true) int numberOfAdults,
			@RequestParam(value="date", required=true) String dateStr,
			@RequestParam(value="startTime", required=true) String startTimeStr,
			@RequestParam(value="endTime", required=true) String endTimeStr) {
		
				LocalDate date = LocalDate.parse(dateStr);
				LocalTime startTime = LocalTime.parse(startTimeStr);
				LocalTime endTime = LocalTime.parse(endTimeStr);
				
				try {
					List<ServiceOfferDTOres> services = serviecOfferService.findAllPlayroomByBookingDetails(city, numberOfKids, numberOfAdults, date, startTime, endTime);
					return new ResponseEntity<>(services, HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
				}
				
			}
	
}
