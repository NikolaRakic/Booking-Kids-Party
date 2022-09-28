package com.diplomski.bookingkidsparty.app.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceOfferRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceOfferResponseDTO;
import com.diplomski.bookingkidsparty.app.service.ServiceOfferService;

import javassist.NotFoundException;

@RestController
@RequestMapping("serviceOffers")
@RequiredArgsConstructor
public class ServiceOfferController {

    private final ServiceOfferService serviecOfferService;

    @GetMapping
    public ResponseEntity<List<ServiceOfferResponseDTO>> getAll() {
        List<ServiceOfferResponseDTO> serviceOffersDto = serviecOfferService.findAll();
        return new ResponseEntity<>(serviceOffersDto, HttpStatus.OK);
    }

    @GetMapping("/type/{additionalOffersType}")
    public ResponseEntity<List<ServiceOfferResponseDTO>> getAdditionalOffersByPlayroom(
            @PathVariable("additionalOffersType") String additionalOffersType,
            @RequestParam(value = "playroomOfferId", required = true) UUID playroomOfferId,
            @RequestParam(value = "city", required = true) String city,
            @RequestParam(value = "numberOfKids", required = true) int numberOfKids,
            @RequestParam(value = "numberOfAdults", required = true) int numberOfAdults,
            @RequestParam(value = "date", required = true) String dateStr,
            @RequestParam(value = "startTime", required = true) String startTimeStr,
            @RequestParam(value = "endTime", required = true) String endTimeStr
    ) {
        LocalDate date = LocalDate.parse(dateStr);
        LocalTime startTime = LocalTime.parse(startTimeStr);
        LocalTime endTime = LocalTime.parse(endTimeStr);

        List<ServiceOfferResponseDTO> serviceOffersDto = serviecOfferService.findAdditionalOffersByPlayroom(
                playroomOfferId, additionalOffersType, city, numberOfKids, numberOfAdults, date, startTime, endTime);
        return new ResponseEntity<>(serviceOffersDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") UUID id) {
        ServiceOfferResponseDTO serviceOfferDto = serviecOfferService.findById(id);
        return new ResponseEntity<>(serviceOfferDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ServiceOfferRequestDTO serviceOfferDTOreq) {
        ServiceOfferResponseDTO serviceOfferResponseDTO = serviecOfferService.add(serviceOfferDTOreq);
        return new ResponseEntity<>(serviceOfferResponseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(serviecOfferService.delete(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") UUID id, @RequestBody ServiceOfferRequestDTO serviceOfferDTO) {
            ServiceOfferResponseDTO serviceOfferResponseDTO = serviecOfferService.edit(id, serviceOfferDTO);
            return new ResponseEntity<>(serviceOfferResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/serviceProvider/{id}")
    public ResponseEntity<?> getAllByServiceProvider(@PathVariable("id") UUID id) {
        List<ServiceOfferResponseDTO> serviceOffersDto = serviecOfferService.findAllByServiceProvider(id);
        return new ResponseEntity<>(serviceOffersDto, HttpStatus.OK);
    }

    @GetMapping("/bookingDetails")
    public ResponseEntity<?> getAllByBookingDetails(
            @RequestParam(value = "city", required = true) String city,
            @RequestParam(value = "numberOfKids", required = true) int numberOfKids,
            @RequestParam(value = "numberOfAdults", required = true) int numberOfAdults,
            @RequestParam(value = "date", required = true) String dateStr,
            @RequestParam(value = "startTime", required = true) String startTimeStr,
            @RequestParam(value = "endTime", required = true) String endTimeStr) {

        LocalDate date = LocalDate.parse(dateStr);
        LocalTime startTime = LocalTime.parse(startTimeStr);
        LocalTime endTime = LocalTime.parse(endTimeStr);

        List<ServiceOfferResponseDTO> services = serviecOfferService.findAllPlayroomByBookingDetails(
                city, numberOfKids, numberOfAdults, date, startTime, endTime);
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

}
