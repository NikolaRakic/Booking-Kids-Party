package com.diplomski.bookingkidsparty.app.dto.response;

import java.util.UUID;

import com.diplomski.bookingkidsparty.app.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParentResponseDTO {

	private UUID id;
	private String name;
	private String surname;
	private String username;
	private String email;
	private String telephoneNumber;
	private boolean blocked;
	private Role userRole;
}
