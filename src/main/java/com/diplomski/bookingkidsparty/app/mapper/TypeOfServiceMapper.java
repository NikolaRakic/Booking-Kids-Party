package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.TypeOfServiceProviderDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.TypeOfServiceProviderDTOres;
import com.diplomski.bookingkidsparty.app.model.TypeOfServiceProvider;


@Component
public class TypeOfServiceMapper {
	
	@Autowired
	ModelMapper modelMapper;
	
	public TypeOfServiceProvider dtoToEntity(TypeOfServiceProviderDTOreq typeOfServiceDTO) throws Exception {
		return modelMapper.map(typeOfServiceDTO, TypeOfServiceProvider.class);
	}

	public TypeOfServiceProviderDTOres entityToDTO(TypeOfServiceProvider typeOfServiceProvider) {
		return modelMapper.map(typeOfServiceProvider, TypeOfServiceProviderDTOres.class);
	}
		
	public List<TypeOfServiceProviderDTOres> ListToListDTO(List<TypeOfServiceProvider> types){
		List<TypeOfServiceProviderDTOres> typesDTO = new ArrayList<TypeOfServiceProviderDTOres>();
		for (TypeOfServiceProvider typeOfServiceProvider : types) {
			typesDTO.add(entityToDTO(typeOfServiceProvider));
		}
		return typesDTO;
	}
}
