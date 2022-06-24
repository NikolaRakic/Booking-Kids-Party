package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.ParentRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ParentResponseDTO;
import com.diplomski.bookingkidsparty.app.model.Parent;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.model.enums.Role;
import com.diplomski.bookingkidsparty.app.security.WebSecurityConfig;

@Component
public class ParentMapper {

	@Autowired
	WebSecurityConfig configuration;
	
	@Autowired
	ModelMapper modelMapper;
	
	public Parent DTOreqToEntity(ParentRequestDTO parentDTOreq) {
		Parent parent = new Parent();
		parent.setEmail(parentDTOreq.getEmail());
		parent.setName(parentDTOreq.getName());
		parent.setUsername(parentDTOreq.getUsername());
		parent.setSurname(parentDTOreq.getSurname());
		parent.setPassword(configuration.passwordEncoder().encode(parentDTOreq.getPassword()));
		parent.setTelephoneNumber(parentDTOreq.getTelephoneNumber());
		parent.setUserRole(Role.ROLE_USER);
		parent.setBlocked(false);
		
		return parent;
	}
	
	public ParentResponseDTO EntityToDTOres(Parent parent) {
		return modelMapper.map(parent, ParentResponseDTO.class);
	}
	
	public List<ParentResponseDTO> ListToListDTO(List<Parent> parents){
		List<ParentResponseDTO> parentsDTO = new ArrayList<ParentResponseDTO>();
		for (Parent parent : parents) {
			parentsDTO.add(EntityToDTOres(parent));
		}
		return parentsDTO;
	}
}
