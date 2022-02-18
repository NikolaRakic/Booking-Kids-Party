package com.diplomski.bookingkidsparty.app.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PhotoDTOres {

	private UUID id;
	private String name;
	private UUID serviceProviderId;
	private String path;
}
