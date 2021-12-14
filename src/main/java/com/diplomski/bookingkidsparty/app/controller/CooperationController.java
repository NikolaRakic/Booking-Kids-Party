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
import org.springframework.web.bind.annotation.RequestBody;

import com.diplomski.bookingkidsparty.app.dto.request.CooperationDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderDTOres;
import com.diplomski.bookingkidsparty.app.service.CooperationService;

@Controller
public class CooperationController {

	@Autowired
	CooperationService cooperationService;
	
	@PostMapping("/cooperation")
	public ResponseEntity<?> add(@RequestBody CooperationDTOreq cooperationDTOreq) {
		try {
			cooperationService.add(cooperationDTOreq);
			return new ResponseEntity<UUID>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/cooperation/{serviceProviderId}")
	public ResponseEntity<List<ServiceProviderDTOres>> getAllByServiceProvider(@PathVariable("serviceProviderId") UUID serviceProviderId){
		List<ServiceProviderDTOres> services = cooperationService.findAllByServiceProvider(serviceProviderId);
		return new ResponseEntity<List<ServiceProviderDTOres>>(services, HttpStatus.OK);
	}
	
	@DeleteMapping("/cooperation")
	public ResponseEntity<?> delete(@RequestBody CooperationDTOreq cooperationDTOreq){
		return new ResponseEntity<>(cooperationService.delete(cooperationDTOreq) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
	}
}
