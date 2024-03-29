package com.diplomski.bookingkidsparty.app.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.diplomski.bookingkidsparty.app.dto.request.ReservationRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.PageableResponse;
import com.diplomski.bookingkidsparty.app.dto.response.ReservationResponseDTO;
import com.diplomski.bookingkidsparty.app.service.ReservationService;

@RestController
@RequestMapping("reservations")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping
	public ResponseEntity<?> add(@RequestBody ReservationRequestDTO reservationDTOreq) {
			UUID reservationId = reservationService.add(reservationDTOreq);
			return new ResponseEntity<>(reservationId, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<?> getAll(Pageable pageable) {
		PageableResponse pageableResponse = reservationService.getAll(pageable);
		return ResponseEntity.ok().headers(pageableResponse.getHeader()).body(pageableResponse.getList());
	}
	
	@GetMapping("/{playRoomId}")
	public ResponseEntity<List<ReservationResponseDTO>> getAllByParty(
			@PathVariable("playRoomId") UUID playRoomId,
			@RequestParam(value="dateOfReservation", required=true) String dateOfReservationStr,
			@RequestParam(value="startTime", required=true) String startTimeStr) {
		LocalDate dateOfReservation = LocalDate.parse(dateOfReservationStr);
		LocalTime startTime = LocalTime.parse(startTimeStr);
		List<ReservationResponseDTO> reservationByParty = reservationService.getAllByParty(playRoomId, dateOfReservation,
				startTime);
		return new ResponseEntity<>(reservationByParty, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id){
		return new ResponseEntity<>(reservationService.delete(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/serviceProvider/{serviceProviderId}")
	public ResponseEntity<?> getAllByServisProvider(
			@PathVariable("serviceProviderId") UUID serviceProviderId, @RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize) {
		PageableResponse pageableResponse = reservationService.getAllByServisProvider(serviceProviderId, pageNo, pageSize );
		return ResponseEntity.ok().headers(pageableResponse.getHeader()).body(pageableResponse.getList());
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getAllByUser(@PathVariable("userId") UUID userId,
			Pageable pageable) {
		PageableResponse pageableResponse = reservationService.getAllByUser(userId, pageable);
		return ResponseEntity.ok().headers(pageableResponse.getHeader()).body(pageableResponse.getList());
	}

}
