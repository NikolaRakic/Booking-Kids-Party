package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.ParentRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ParentResponseDTO;

import javassist.NotFoundException;

public interface ParentService {

	ParentResponseDTO registration(ParentRequestDTO userDTOreq);

	ParentResponseDTO edit(UUID id, ParentRequestDTO userDTOreq);
	
	ParentResponseDTO findById(UUID id);

	List<ParentResponseDTO> findAll();
}
