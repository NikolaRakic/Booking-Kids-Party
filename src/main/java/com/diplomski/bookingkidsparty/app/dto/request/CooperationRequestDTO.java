package com.diplomski.bookingkidsparty.app.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CooperationRequestDTO {
	
	private UUID playroomId;
	private UUID cooperationServiceId;

}
