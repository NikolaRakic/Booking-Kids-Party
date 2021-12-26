package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.LoginDTOreq;
import com.diplomski.bookingkidsparty.app.dto.request.UserDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.LoggedInUserDTOres;
import com.diplomski.bookingkidsparty.app.dto.response.UserDTOres;
import com.diplomski.bookingkidsparty.app.mapper.UserMapper;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.repository.UserRepository;
//import com.diplomski.bookingkidsparty.app.security.SecurityConfiguration;
import com.diplomski.bookingkidsparty.app.security.TokenUtils;
import com.diplomski.bookingkidsparty.app.service.UserService;

import javassist.NotFoundException;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserMapper userMapper;
//	@Autowired
//	SecurityConfiguration configuration;
//	@Autowired
//	AuthenticationManager authenticationManager;
	@Qualifier("userDetailsServiceImpl")
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	TokenUtils tokenUtils;
	
	@Override
	public UUID registration(UserDTOreq userDTOreq) throws Exception {
		User user = userMapper.DTOreqToEntity(userDTOreq);
		Optional<User> userOptional = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
		if(!userOptional.isPresent()) {
			userRepository.saveAndFlush(user);
			return user.getId();
		}
		throw new Exception("User with this username or email arleady exist!");
	}

	@Override
	public UserDTOres findById(UUID id) throws NotFoundException {
		Optional<User> userOptional = userRepository.findById(id);
		if(userOptional.isPresent()) {
			return userMapper.EntityToDTOres(userOptional.get());
		}
		throw new NotFoundException("User with this id doesn't exist!");
	}

	@Override
	public List<UserDTOres> findAll() {
		return userMapper.ListToListDTO(userRepository.findAll());
	}

	@Override
	public void edit(UUID id, UserDTOreq userDTOreq) throws NotFoundException {
		Optional<User> userOptional = userRepository.findById(id);
		if(userOptional.isPresent()) {
			User userForEdit = userOptional.get();
			userForEdit.setBlocked(userDTOreq.isBlocked());
			userForEdit.setEmail(userDTOreq.getEmail());
			userForEdit.setName(userDTOreq.getName());
			//userForEdit.setPassword(configuration.passwordEncoder().encode(userDTOreq.getPassword()));
			userForEdit.setSurname(userDTOreq.getSurname());
			userForEdit.setTelephoneNumber(userDTOreq.getTelephoneNumber());
			userForEdit.setUsername(userDTOreq.getUsername());
			
			userRepository.saveAndFlush(userForEdit);
		}else {
			throw new NotFoundException("User with this id doesn't exist!");
		}
	}

	@Override
	public boolean delete(UUID id) {
		Optional<User> userOptional = userRepository.findById(id);
		if(userOptional.isPresent()) {
			userRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public User findByUsernameOrEmail(String userNameOrEmail) {
		Optional<User> userOptional = userRepository.findByUsernameOrEmail(userNameOrEmail, userNameOrEmail);
		if(userOptional.isPresent()) {
			return userOptional.get();
		}
		return null;
	}

	@Override
	public LoggedInUserDTOres login(LoginDTOreq loginDTOreq) {
		System.out.println(loginDTOreq.getUserNameOrEmail() + " " + loginDTOreq.getPassword());
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTOreq.getUserNameOrEmail(), loginDTOreq.getPassword());
		//Authentication authentication = authenticationManager.authenticate(token);
		//SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTOreq.getUserNameOrEmail());
		User userFromDb = findByUsernameOrEmail(loginDTOreq.getUserNameOrEmail());
		String role = userFromDb.getUserRole().toString();
		System.out.println("ULOGA JE " + role);
		LoggedInUserDTOres loggedIn = new LoggedInUserDTOres(userFromDb.getId(), tokenUtils.generateToken(userDetails), userFromDb.getUsername(), userFromDb.getEmail(), role, userDetails.getAuthorities());
		System.out.println(loggedIn.getAuthorities());
		return loggedIn;
	}

}
