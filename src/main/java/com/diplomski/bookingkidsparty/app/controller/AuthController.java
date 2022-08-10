package com.diplomski.bookingkidsparty.app.controller;

import com.diplomski.bookingkidsparty.app.dto.response.ParentResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.diplomski.bookingkidsparty.app.dto.request.LoginRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.request.ParentRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.request.UserTokenStateDTO;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.security.TokenUtils;
import com.diplomski.bookingkidsparty.app.service.ParentService;
import com.diplomski.bookingkidsparty.app.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private ParentService parentService;

	@PostMapping("/login")
	public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody LoginRequestDTO loginDTOreq,
			HttpServletResponse response) {
		UserTokenStateDTO userTokenStateDTO = userService.login(loginDTOreq);
		return new ResponseEntity<>(userTokenStateDTO, HttpStatus.OK);
	}
	
	@PostMapping("/parent/signup")
	public ResponseEntity<ParentResponseDTO> registrationParent(@RequestBody ParentRequestDTO parentRequest, UriComponentsBuilder ucBuilder){
		ParentResponseDTO parentDTO = parentService.registration(parentRequest);
		return new ResponseEntity<>(parentDTO, HttpStatus.CREATED);
	}
	
}
