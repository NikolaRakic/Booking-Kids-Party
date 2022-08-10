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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diplomski.bookingkidsparty.app.dto.request.ParentRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ParentResponseDTO;
import com.diplomski.bookingkidsparty.app.service.ParentService;

import javassist.NotFoundException;

@RestController
@RequestMapping("parents")
public class ParentController {

    @Autowired
    ParentService parentService;

    @PostMapping
    public ResponseEntity<ParentResponseDTO> registration(@RequestBody ParentRequestDTO parentDTOreq) {
        ParentResponseDTO parentResponseDTO = parentService.registration(parentDTOreq);
        return new ResponseEntity<>(parentResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") UUID id, @RequestBody ParentRequestDTO parentDTOreq) {
            ParentResponseDTO parentResponseDTO = parentService.edit(id, parentDTOreq);
            return new ResponseEntity<>(parentResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") UUID id) {
        ParentResponseDTO parentDTOres = parentService.findById(id);
        return new ResponseEntity<>(parentDTOres, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ParentResponseDTO>> findAll() {
        List<ParentResponseDTO> parentsDTOres = parentService.findAll();
        return new ResponseEntity<>(parentsDTOres, HttpStatus.OK);
    }
}
