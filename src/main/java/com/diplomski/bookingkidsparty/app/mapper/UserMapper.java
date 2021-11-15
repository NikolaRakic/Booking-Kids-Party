package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.UserDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.UserDTOres;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.security.SecurityConfiguration;
import com.diplomski.bookingkidsparty.app.util.Role;

@Component
public class UserMapper {

	@Autowired
	SecurityConfiguration configuration;
	
	public User convertDTOreqToEntity(UserDTOreq userDTOreq) {
		User user = new User();
		user.setEmail(userDTOreq.getEmail());
		user.setName(userDTOreq.getName());
		user.setUsername(userDTOreq.getUsername());
		user.setSurname(userDTOreq.getSurname());
		user.setPassword(configuration.passwordEncoder().encode(userDTOreq.getPassword()));
		user.setTelephoneNumber(userDTOreq.getTelephoneNumber());
		user.setUserRole(Role.USER);
		user.setBlocked(false);
		
		return user;
	}
	
	public UserDTOres convertEntityToDTOres(User user) {
		UserDTOres userDTOres = new UserDTOres();
		userDTOres.setBlocked(user.isBlocked());
		userDTOres.setEmail(user.getEmail());
		userDTOres.setId(user.getId());
		userDTOres.setName(user.getName());
		userDTOres.setPassword(user.getPassword());
		userDTOres.setSurname(user.getSurname());
		userDTOres.setTelephoneNumber(user.getTelephoneNumber());
		userDTOres.setUsername(user.getUsername());
		userDTOres.setUserRole(user.getUserRole());
		
		return userDTOres;
	}
	
	public List<UserDTOres> convertListToListDTO(List<User> users){
		List<UserDTOres> usersDTO = new ArrayList<UserDTOres>();
		for (User user : users) {
			usersDTO.add(convertEntityToDTOres(user));
		}
		return usersDTO;
	}
}
