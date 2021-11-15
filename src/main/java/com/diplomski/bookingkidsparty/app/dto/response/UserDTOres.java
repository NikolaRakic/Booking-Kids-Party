package com.diplomski.bookingkidsparty.app.dto.response;

import java.util.UUID;

import com.diplomski.bookingkidsparty.app.util.Role;

import lombok.Data;

@Data
public class UserDTOres {

	private UUID id;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String email;
	private String telephoneNumber;
	private boolean blocked;
	private Role userRole;
}
