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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.diplomski.bookingkidsparty.app.dto.request.RatingDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.StarRatingDTOres;
import com.diplomski.bookingkidsparty.app.dto.response.RatingDTOres;
import com.diplomski.bookingkidsparty.app.service.RatingService;

@Controller
public class RatingController {

	@Autowired
	RatingService ratingService;
	
	@PostMapping("/rating")
	public ResponseEntity<?> create(@RequestBody RatingDTOreq ratingDto){
		try {
			UUID id = ratingService.create(ratingDto);
			return new ResponseEntity<UUID>(id, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/rating/{serviceProviderId}")
	public ResponseEntity<List<RatingDTOres>> getAllRatingForServiceProvider(@PathVariable("serviceProviderId") UUID serviceProviderId){
		List<RatingDTOres> ratings = ratingService.getAllByServiceProvider(serviceProviderId);
		return new ResponseEntity<List<RatingDTOres>>(ratings, HttpStatus.OK);
	}
	
	@GetMapping("/rating")
	public ResponseEntity<StarRatingDTOres> getAverageRatingForServiceProvider(
			@RequestParam(value="serviceProviderId", required=true) UUID serviceProviderId){
		StarRatingDTOres averageRating = ratingService.getAverageRatingByServiceProvider(serviceProviderId);
		return new ResponseEntity<StarRatingDTOres>(averageRating, HttpStatus.OK);
	}
	
	@DeleteMapping("/rating/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id){
		return new ResponseEntity<>(ratingService.delete(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
	}
	
}
