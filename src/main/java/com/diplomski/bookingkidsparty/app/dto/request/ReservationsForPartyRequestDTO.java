package com.diplomski.bookingkidsparty.app.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ReservationsForPartyRequestDTO {
	
	UUID playRoomId;
	LocalDate dateOfReservation;
	LocalTime startTime;

}
