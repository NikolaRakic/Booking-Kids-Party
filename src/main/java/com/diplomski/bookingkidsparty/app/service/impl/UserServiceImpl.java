package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.UserDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.UserDTOres;
import com.diplomski.bookingkidsparty.app.mapper.UserMapper;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.repository.UserRepository;
import com.diplomski.bookingkidsparty.app.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserMapper userMapper;
	
	@Override
	public UUID registration(UserDTOreq userDTOreq) {
		User user = userMapper.convertDTOreqToEntity(userDTOreq);
		userRepository.saveAndFlush(user);
		return user.getId();
	}

	@Override
	public UserDTOres findOne(UUID id) {
		Optional<User> userOptional = userRepository.findById(id);
		if(userOptional.isPresent()) {
			return userMapper.convertEntityToDTOres(userOptional.get());
		}
		return null;
	}

	@Override
	public List<UserDTOres> findAll() {
		return userMapper.convertListToListDTO(userRepository.findAll());
	}

}
