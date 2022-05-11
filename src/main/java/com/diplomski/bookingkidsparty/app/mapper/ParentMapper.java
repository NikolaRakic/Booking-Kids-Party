package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.ParentDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.ParentDTOres;
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
	
	public Parent DTOreqToEntity(ParentDTOreq parentDTOreq) {
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
	
	public ParentDTOres EntityToDTOres(Parent parent) {
		return modelMapper.map(parent, ParentDTOres.class);
	}
	
	public List<ParentDTOres> ListToListDTO(List<Parent> parents){
		List<ParentDTOres> parentsDTO = new ArrayList<ParentDTOres>();
		for (Parent parent : parents) {
			parentsDTO.add(EntityToDTOres(parent));
		}
		return parentsDTO;
	}
}
