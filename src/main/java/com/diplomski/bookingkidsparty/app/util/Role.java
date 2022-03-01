package com.diplomski.bookingkidsparty.app.util;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

	ROLE_USER, ROLE_ADMINISTRATOR, ROLE_SERVICE_PROVIDER;

	@Override
	public String getAuthority() {
		return name();
	}
}
