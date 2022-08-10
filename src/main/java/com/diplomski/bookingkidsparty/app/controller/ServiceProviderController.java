package com.diplomski.bookingkidsparty.app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderEditDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderOnePhotoResponseDTO;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.service.ServiceProviderService;
import com.diplomski.bookingkidsparty.app.util.GeneratePassword;

import javassist.NotFoundException;

@RestController
@RequestMapping("serviceProviders")
public class ServiceProviderController {

	@Autowired
	ServiceProviderService serviceProviderService;
	
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	@PostMapping
	public ResponseEntity<?> add(@RequestBody ServiceProviderRequestDTO serviceProviderDTO){
			ServiceProviderResponseDTO serviceProviderResponseDTO = serviceProviderService.add(serviceProviderDTO);
			return new ResponseEntity<>(serviceProviderResponseDTO, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping
	public ResponseEntity<List<ServiceProviderOnePhotoResponseDTO>> getAll(){
			List<ServiceProviderOnePhotoResponseDTO> services = serviceProviderService.findAll();
			return new ResponseEntity<>(services, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") UUID id){
			ServiceProviderResponseDTO serviceProviderDTO = serviceProviderService.findById(id);
			return new ResponseEntity<>(serviceProviderDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") UUID id){
		serviceProviderService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('SERVICE_PROVIDER')")
	public ResponseEntity<?> edit(@PathVariable("id") UUID id, @RequestBody ServiceProviderEditDTO serviceProviderDTO){
			ServiceProviderResponseDTO serviceProviderResponseDTO = serviceProviderService.edit(id, serviceProviderDTO);
			return new ResponseEntity<>(serviceProviderResponseDTO, HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('SERVICE_PROVIDER')")
	@GetMapping("/typeByServiceProviderId/{id}")
	public ResponseEntity<?> getServiceProviderType(@PathVariable("id") UUID id){
		String type = serviceProviderService.getType(id);
		return new ResponseEntity<>(type, HttpStatus.OK);
	}
	
	
	@GetMapping("/type/{type}")
	public ResponseEntity<?> getAllByType(@PathVariable("type") TypeOfServiceProvider typeOfServiceProvider){
				List<ServiceProviderOnePhotoResponseDTO> typesDTO = serviceProviderService.findAllByType(typeOfServiceProvider);
				return new ResponseEntity<>(typesDTO, HttpStatus.OK);
	}
	
}
