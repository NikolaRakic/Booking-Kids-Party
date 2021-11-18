package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.LoginDTOreq;
import com.diplomski.bookingkidsparty.app.dto.request.UserDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.LoggedInUserDTOres;
import com.diplomski.bookingkidsparty.app.dto.response.UserDTOres;
import com.diplomski.bookingkidsparty.app.model.User;

public interface UserService {

	UUID registration(UserDTOreq userDTOreq);

	UserDTOres findOne(UUID id);

	List<UserDTOres> findAll();

	void edit(UUID id, UserDTOreq userDTOreq);

	boolean delete(UUID id);

	User findByUsernameOrEmail(String userNameOrEmail);
	
	LoggedInUserDTOres login(LoginDTOreq loginDTOreq);

}
