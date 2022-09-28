package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.LoginRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.request.UserTokenStateDTO;
import com.diplomski.bookingkidsparty.app.security.TokenUtils;
import com.diplomski.bookingkidsparty.app.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.mapper.ParentMapper;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.repository.UserRepository;
import com.diplomski.bookingkidsparty.app.service.UserService;

@Service("UserServiceImpl")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService{

	protected final Log LOGGER = LogFactory.getLog(getClass());

	private final UserRepository userRepository;
	private final TokenUtils tokenUtils;
	private final AuthenticationManager authenticationManager;

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
	public UserTokenStateDTO login(LoginRequestDTO loginDTOreq) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTOreq.getUsernameOrEmail(),
						loginDTOreq.getPassword()));

		// Ubaci korisnika u trenutni security kontekst
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token za tog korisnika
		User user = (User) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getUsername(), user.getId(), user.getUserRole().toString());
		Long expiresIn = tokenUtils.getExpiredIn();
		System.out.println(user.getAuthorities());
		// Vrati token kao odgovor na uspesnu autentifikaciju
		return new UserTokenStateDTO(jwt, expiresIn);
	}

	@Override
	public User findByUsernameOrEmail(String userNameOrEmail) {
		return userRepository.findByUsernameOrEmail(userNameOrEmail)
				.orElseThrow(()-> new UsernameNotFoundException(String.format("No user found with username '%s'.", userNameOrEmail)));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsernameOrEmail(username)
				.orElseThrow(()-> new UsernameNotFoundException(String.format("No user found with username '%s'.", username)));
	}


}
