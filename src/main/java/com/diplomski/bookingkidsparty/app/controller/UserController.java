package com.diplomski.bookingkidsparty.app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.diplomski.bookingkidsparty.app.dto.request.LoginDTOreq;
import com.diplomski.bookingkidsparty.app.dto.request.UserDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.LoggedInUserDTOres;
import com.diplomski.bookingkidsparty.app.dto.response.UserDTOres;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.security.TokenUtils;
import com.diplomski.bookingkidsparty.app.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/user")
	public ResponseEntity<UUID> registration (@RequestBody UserDTOreq userDTOreq){
		UUID id = userService.registration(userDTOreq);
		return new ResponseEntity<UUID>(id, HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<UserDTOres> findOne(@PathVariable("id") UUID id){
		UserDTOres userDTOres = userService.findOne(id);
		return new ResponseEntity<UserDTOres>(userDTOres, HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<UserDTOres>> findAll(){
		List<UserDTOres> usersDTOres = userService.findAll();
		return new ResponseEntity<List<UserDTOres>>(usersDTOres, HttpStatus.OK);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<?> edit(@PathVariable("id") UUID id, @RequestBody UserDTOreq userDTOreq){
		try {
			userService.edit(id, userDTOreq);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (NotFound e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);	
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);	
		}
	}
	
	@DeleteMapping("user/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id) throws Exception{
		if(userService.delete(id)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value="/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody LoginDTOreq loginDTO){
		LoggedInUserDTOres loggedIn = userService.login(loginDTO);
		if(loggedIn == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(loggedIn, HttpStatus.OK);
	}
	
}
