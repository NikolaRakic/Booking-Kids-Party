package com.diplomski.bookingkidsparty.app.dto.request;

import lombok.Data;

@Data
public class LoginDTOreq {

	private String userNameOrEmail;
	private String password;
}
