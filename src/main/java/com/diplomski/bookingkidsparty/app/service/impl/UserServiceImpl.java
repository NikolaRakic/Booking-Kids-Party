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

import com.diplomski.bookingkidsparty.app.dto.request.ParentDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ParentDTOres;
import com.diplomski.bookingkidsparty.app.mapper.ParentMapper;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.repository.UserRepository;
import com.diplomski.bookingkidsparty.app.security.TokenUtils;
import com.diplomski.bookingkidsparty.app.service.UserService;

import javassist.NotFoundException;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService{

	protected final Log LOGGER = LogFactory.getLog(getClass());
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ParentMapper userMapper;
	
	@Qualifier("userDetailsServiceImpl")
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	TokenUtils tokenUtils;
	//-*-*-*-*-*-*-*-*-
//	@Override
//	public UUID registration(ParentDTOreq userDTOreq) throws Exception {
//		User user = userMapper.DTOreqToEntity(userDTOreq);
//		Optional<User> userOptional = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
//		if(!userOptional.isPresent()) {
//			userRepository.saveAndFlush(user);
//			return user.getId();
//		}
//		throw new Exception("User with this username or email arleady exist!");
//	}

//	@Override
//	public ParentDTOres findById(UUID id) throws NotFoundException {
//		Optional<User> userOptional = userRepository.findById(id);
//		if(userOptional.isPresent()) {
//			return userMapper.EntityToDTOres(userOptional.get());
//		}
//		throw new NotFoundException("User with this id doesn't exist!");
//	}
//
//	@Override
//	public List<ParentDTOres> findAll() {
//		return userMapper.ListToListDTO(userRepository.findAll());
//	}



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
		Optional<User> userOptional = userRepository.findByUsernameOrEmail(userNameOrEmail);
		if(userOptional.isPresent()) {
			return userOptional.get();
		}
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loaduserbyusername -> userService");
		Optional<User> userOptional = userRepository.findByUsernameOrEmail(username);
		if (!userOptional.isPresent()) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return userOptional.get();
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
