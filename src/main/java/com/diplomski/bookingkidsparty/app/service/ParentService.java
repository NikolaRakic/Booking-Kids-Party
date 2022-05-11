package com.diplomski.bookingkidsparty.app.service;

import java.util.List;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.dto.request.ParentDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ParentDTOres;

import javassist.NotFoundException;

public interface ParentService {

	UUID registration(ParentDTOreq userDTOreq) throws Exception;
	
	void edit(UUID id, ParentDTOreq userDTOreq) throws NotFoundException;
	
	ParentDTOres findById(UUID id) throws NotFoundException;

	List<ParentDTOres> findAll();
}
