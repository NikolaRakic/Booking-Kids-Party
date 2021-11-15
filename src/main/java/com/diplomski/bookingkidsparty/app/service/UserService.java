package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.UserDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.UserDTOres;

public interface UserService {

	UUID registration(UserDTOreq userDTOreq);

	UserDTOres findOne(UUID id);

	List<UserDTOres> findAll();

}
