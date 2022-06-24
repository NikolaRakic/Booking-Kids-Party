package com.diplomski.bookingkidsparty.app.dto.request;

import lombok.Data;

@Data
public class LoginRequestDTO {

	private String usernameOrEmail;
	private String password;
}
