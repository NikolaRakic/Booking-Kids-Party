package com.diplomski.bookingkidsparty.app.dto.response;

import java.util.UUID;

import com.diplomski.bookingkidsparty.app.model.ServiceProvider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class CooperationResponseDTO {

	private UUID playroomId;
	
	private UUID cooperationServiceId;
	
	private boolean confirmed;
	
	private UUID requestSender;
}
