package com.diplomski.bookingkidsparty.app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.diplomski.bookingkidsparty.app.dto.request.LoginDTOreq;
import com.diplomski.bookingkidsparty.app.dto.request.UserDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.LoggedInUserDTOres;
import com.diplomski.bookingkidsparty.app.dto.response.UserDTOres;
import com.diplomski.bookingkidsparty.app.service.UserService;

import javassist.NotFoundException;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/user")
	public ResponseEntity<?> registration (@RequestBody UserDTOreq userDTOreq){
		UUID id;
		try {
			id = userService.registration(userDTOreq);
			return new ResponseEntity<UUID>(id, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") UUID id){
		try {
			UserDTOres userDTOres = userService.findById(id);
			return new ResponseEntity<UserDTOres>(userDTOres, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);	
		}		
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
		} catch (NotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);	
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);	
		}
	}
	
	@DeleteMapping("user/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id) throws Exception{
		return new ResponseEntity<>(userService.delete(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
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
