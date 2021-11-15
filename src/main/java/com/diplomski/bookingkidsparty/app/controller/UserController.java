package com.diplomski.bookingkidsparty.app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.diplomski.bookingkidsparty.app.dto.request.UserDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.UserDTOres;
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
	
}
