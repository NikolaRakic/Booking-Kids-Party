package com.diplomski.bookingkidsparty.app.controller;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @PostMapping
    public ResponseEntity<ParentResponseDTO> registration(@RequestBody ParentRequestDTO parentDTOreq) {
        return new ResponseEntity<>(parentService.registration(parentDTOreq), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentResponseDTO> edit(@PathVariable("id") UUID id, @RequestBody ParentRequestDTO parentDTOreq) {
            return new ResponseEntity<>(parentService.edit(id, parentDTOreq), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentResponseDTO> getOne(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(parentService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ParentResponseDTO>> getAll() {
        return new ResponseEntity<>(parentService.findAll(), HttpStatus.OK);
    }
}


