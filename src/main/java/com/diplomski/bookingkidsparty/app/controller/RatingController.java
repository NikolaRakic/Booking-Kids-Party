package com.diplomski.bookingkidsparty.app.controller;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diplomski.bookingkidsparty.app.dto.request.RatingRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.StarRatingResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.RatingResponseDTO;
import com.diplomski.bookingkidsparty.app.service.RatingService;

@RestController
@RequestMapping("ratings")
@RequiredArgsConstructor
public class RatingController {

	private final RatingService ratingService;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody RatingRequestDTO ratingDto){
			RatingResponseDTO ratingResponseDTO = ratingService.create(ratingDto);
			return new ResponseEntity<>(ratingResponseDTO, HttpStatus.CREATED);
	}
	
	@GetMapping("/{serviceProviderId}")
	public ResponseEntity<List<RatingResponseDTO>> getAllRatingForServiceProvider(@PathVariable("serviceProviderId") UUID serviceProviderId){
		List<RatingResponseDTO> ratings = ratingService.getAllByServiceProvider(serviceProviderId);
		return new ResponseEntity<>(ratings, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<StarRatingResponseDTO> getAverageRatingForServiceProvider(
			@RequestParam(value="serviceProviderId", required=true) UUID serviceProviderId){
		StarRatingResponseDTO averageRating = ratingService.getAverageRatingByServiceProvider(serviceProviderId);
		return new ResponseEntity<>(averageRating, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id){
		return new ResponseEntity<>(ratingService.delete(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
	}
	
}
