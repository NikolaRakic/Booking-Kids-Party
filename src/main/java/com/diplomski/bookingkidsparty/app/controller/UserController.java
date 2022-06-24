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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diplomski.bookingkidsparty.app.dto.request.LoginRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.request.ParentRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.LoggedInUserResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ParentResponseDTO;
import com.diplomski.bookingkidsparty.app.service.UserService;

import javassist.NotFoundException;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	UserService userService;
	
//	@GetMapping("/user/{id}")
//	public ResponseEntity<?> findById(@PathVariable("id") UUID id){
//		try {
//			ParentDTOres userDTOres = userService.findById(id);
//			return new ResponseEntity<ParentDTOres>(userDTOres, HttpStatus.OK);
//		} catch (NotFoundException e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);	
//		}		
//	}
//	
//	@GetMapping("/user")
//	public ResponseEntity<List<ParentDTOres>> findAll(){
//		List<ParentDTOres> usersDTOres = userService.findAll();
//		return new ResponseEntity<List<ParentDTOres>>(usersDTOres, HttpStatus.OK);
//	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id) throws Exception{
		return new ResponseEntity<>(userService.delete(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
	}
	
}
