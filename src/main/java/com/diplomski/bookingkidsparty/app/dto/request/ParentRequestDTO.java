package com.diplomski.bookingkidsparty.app.dto.request;

import lombok.Data;

@Data
public class ParentRequestDTO {

	private String name;
	private String surname;
	private String username;
	private String password;
	private String email;
	private String telephoneNumber;
	private boolean blocked;
}
