package com.diplomski.bookingkidsparty.app.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.diplomski.bookingkidsparty.app.dto.request.LoginDTOreq;
import com.diplomski.bookingkidsparty.app.dto.request.ParentDTOreq;
import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderDTOreq;
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
	private TokenUtils tokenUtils;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ParentService parentService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody LoginDTOreq loginDTOreq,
			HttpServletResponse response) {
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
		return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
	}
	
	@PostMapping("/parent/signup")
	public ResponseEntity<UUID> registrationParent(@RequestBody ParentDTOreq parentRequest, UriComponentsBuilder ucBuilder) throws Exception {
		UUID parentId = parentService.registration(parentRequest);
		return new ResponseEntity<>(parentId, HttpStatus.CREATED);
	}
	
	//@PreAuthorize("hasRole('USER')")
	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger, Principal p, Authentication auths) {
		System.out.println(auths.getAuthorities() + "----------------");
		System.out.println(p.getName() + "TEST ---------");
		System.out.println(SecurityContextHolder.getContext());

		// Kreiraj token za tog korisnika 
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userEmail = auth.getPrincipal().toString();
		System.out.println("email: " + userEmail);
		userService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

		Map<String, String> result = new HashMap();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
	
}
