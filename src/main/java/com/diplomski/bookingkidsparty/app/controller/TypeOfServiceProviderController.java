package com.diplomski.bookingkidsparty.app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.diplomski.bookingkidsparty.app.dto.request.TypeOfServiceProviderDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.TypeOfServiceProviderDTOres;
import com.diplomski.bookingkidsparty.app.service.TypeOfServiceProviderService;

import javassist.NotFoundException;

@Controller("")
public class TypeOfServiceProviderController {
	
	@Autowired
	TypeOfServiceProviderService typeOfServiceProviderService;
	
	@PostMapping("/typesOfServiceProvider")
	public ResponseEntity<UUID> addTypeOfService(@RequestBody TypeOfServiceProviderDTOreq typeOfServiceProviderDTO) throws Exception{
			UUID id = typeOfServiceProviderService.add(typeOfServiceProviderDTO);
			return new ResponseEntity<UUID>(id, HttpStatus.CREATED);
	}
	
	@GetMapping("/typesOfServiceProvider")
	public ResponseEntity<List<TypeOfServiceProviderDTOres>> findAll(){
			List<TypeOfServiceProviderDTOres> typesDTO = typeOfServiceProviderService.findAll();
			return new ResponseEntity<List<TypeOfServiceProviderDTOres>>(typesDTO, HttpStatus.OK);
	}
	
	@GetMapping("/typesOfServiceProvider/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") UUID id) throws Exception{
		try {
			TypeOfServiceProviderDTOres typeOfServiceProviderDTO = typeOfServiceProviderService.findById(id);
			return new ResponseEntity<TypeOfServiceProviderDTOres>(typeOfServiceProviderDTO, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);	
		}			
	}
	
	@DeleteMapping("/typesOfServiceProvider/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id) throws Exception{
		return new ResponseEntity<>(typeOfServiceProviderService.delete(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/typesOfServiceProvider/{id}")
	public ResponseEntity<?> edit(@PathVariable("id") UUID id, @RequestBody TypeOfServiceProviderDTOreq typeOfServiceProviderDTO){
		try {
			typeOfServiceProviderService.edit(id, typeOfServiceProviderDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (NotFound e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);	
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);	
		}
	}
	
	
}
