package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.ParentRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ParentResponseDTO;

import javassist.NotFoundException;

public interface ParentService {

	UUID registration(ParentRequestDTO userDTOreq) throws Exception;
	
	void edit(UUID id, ParentRequestDTO userDTOreq) throws NotFoundException;
	
	ParentResponseDTO findById(UUID id) throws NotFoundException;

	List<ParentResponseDTO> findAll();
}
