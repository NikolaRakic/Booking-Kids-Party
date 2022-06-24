package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.LoginRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.request.ParentRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.LoggedInUserResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ParentResponseDTO;
import com.diplomski.bookingkidsparty.app.model.User;

import javassist.NotFoundException;

public interface UserService {

//	ParentDTOres findById(UUID id) throws NotFoundException;
//
//	List<ParentDTOres> findAll();

	boolean delete(UUID id);

	User findByUsernameOrEmail(String userNameOrEmail);

	void changePassword(String oldPassword, String newPassword);
	

}
