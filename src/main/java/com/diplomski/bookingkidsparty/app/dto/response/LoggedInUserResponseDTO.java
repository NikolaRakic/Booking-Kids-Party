package com.diplomski.bookingkidsparty.app.dto.response;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoggedInUserResponseDTO {

	private UUID id;
	private String token;
	private String username;
	private String email;
	private String role;
	private Collection<? extends GrantedAuthority> authorities;	
}
