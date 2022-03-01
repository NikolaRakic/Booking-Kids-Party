package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.UserDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.UserDTOres;
import com.diplomski.bookingkidsparty.app.mapper.UserMapper;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.repository.UserRepository;
import com.diplomski.bookingkidsparty.app.security.TokenUtils;
import com.diplomski.bookingkidsparty.app.service.UserService;

import javassist.NotFoundException;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{

	protected final Log LOGGER = LogFactory.getLog(getClass());
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserMapper userMapper;
	
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
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return user;
		}
	}
	
	public void changePassword(String oldPassword, String newPassword) {

		// Ocitavamo trenutno ulogovanog korisnika
		System.out.println("*************//////////////////");
		//User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//System.out.println(SecurityContextHolder.getContext().getAuthentication());
		//System.out.println("*************" + currentUser);
		//String username = currentUser.getName();
//		System.out.println("*************" + username);
//
//		if (authenticationManager != null) {
//			LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");
//
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
//		} else {
//			LOGGER.debug("No authentication manager set. can't change Password!");
//
//			return;
//		}
//
//		LOGGER.debug("Changing password for user '" + username + "'");
//
//		User user = (User) loadUserByUsername(username);

		// pre nego sto u bazu upisemo novu lozinku, potrebno ju je hesirati
		// ne zelimo da u bazi cuvamo lozinke u plain text formatu
//		user.setPassword(passwordEncoder.encode(newPassword));
//		userRepository.save(user);

	}


}
