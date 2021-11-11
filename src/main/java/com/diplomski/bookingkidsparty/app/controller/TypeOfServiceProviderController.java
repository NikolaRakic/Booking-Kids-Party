package com.diplomski.bookingkidsparty.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.diplomski.bookingkidsparty.app.model.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.service.TypeOfServiceProviderService;

@Controller("")
public class TypeOfServiceProviderController {
	
	@Autowired
	TypeOfServiceProviderService typeOfServiceProviderService;
	
	@PostMapping("/typeOfServiceProvider")
	public ResponseEntity<String> addTypeOfService(@RequestBody TypeOfServiceProvider request){
		try {
			return new ResponseEntity<String>(typeOfServiceProviderService.add(request), HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/typeOfServiceProvider")
	public ResponseEntity<?> getAll(){
		try {
			return new ResponseEntity<List<TypeOfServiceProvider>>(typeOfServiceProviderService.getAll(), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

}
