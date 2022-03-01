package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.UserDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.UserDTOres;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.security.WebSecurityConfig;
//import com.diplomski.bookingkidsparty.app.security.SecurityConfiguration;
import com.diplomski.bookingkidsparty.app.util.Role;

@Component
public class UserMapper {

	@Autowired
	WebSecurityConfig configuration;
	
	@Autowired
	ModelMapper modelMapper;
	
	public User DTOreqToEntity(UserDTOreq userDTOreq) {
		User user = new User();
		user.setEmail(userDTOreq.getEmail());
		user.setName(userDTOreq.getName());
		user.setUsername(userDTOreq.getUsername());
		user.setSurname(userDTOreq.getSurname());
		user.setPassword(configuration.passwordEncoder().encode(userDTOreq.getPassword()));
		user.setTelephoneNumber(userDTOreq.getTelephoneNumber());
		user.setUserRole(Role.ROLE_USER);
		user.setBlocked(false);
		
		return user;
	}
	
	public UserDTOres EntityToDTOres(User user) {
		return modelMapper.map(user, UserDTOres.class);
	}
	
	public List<UserDTOres> ListToListDTO(List<User> users){
		List<UserDTOres> usersDTO = new ArrayList<UserDTOres>();
		for (User user : users) {
			usersDTO.add(EntityToDTOres(user));
		}
		return usersDTO;
	}
}
