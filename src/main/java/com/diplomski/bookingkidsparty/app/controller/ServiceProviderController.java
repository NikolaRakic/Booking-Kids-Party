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

import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderDTOres;
import com.diplomski.bookingkidsparty.app.service.ServiceProviderService;

@Controller("")
public class ServiceProviderController {

	@Autowired
	ServiceProviderService serviceProviderService;
	
	@PostMapping("/serviceProvider")
	public ResponseEntity<UUID> add(@RequestBody ServiceProviderDTOreq serviceProviderDTO) throws Exception{
			UUID id = serviceProviderService.add(serviceProviderDTO);
			return new ResponseEntity<UUID>(id, HttpStatus.CREATED);
	}
	
	@GetMapping("/serviceProvider")
	public ResponseEntity<List<ServiceProviderDTOres>> findAll(){
			List<ServiceProviderDTOres> typesDTO = serviceProviderService.findAll();
			return new ResponseEntity<List<ServiceProviderDTOres>>(typesDTO, HttpStatus.OK);
	}
	
	@GetMapping("/serviceProvider/{id}")
	public ResponseEntity<ServiceProviderDTOres> findOne(@PathVariable("id") UUID id) throws Exception{
			ServiceProviderDTOres ServiceProviderDTO = serviceProviderService.findOne(id);
			return new ResponseEntity<ServiceProviderDTOres>(ServiceProviderDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/serviceProvider/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id) throws Exception{
		if(serviceProviderService.delete(id)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/serviceProvider/{id}")
	public ResponseEntity<?> edit(@PathVariable("id") UUID id, @RequestBody ServiceProviderDTOres serviceProviderDTO){
		try {
			serviceProviderService.edit(id, serviceProviderDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (NotFound e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);	
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);	
		}
	}
	
	
}
