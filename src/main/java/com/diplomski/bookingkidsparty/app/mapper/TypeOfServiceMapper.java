package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.request.TypeOfServiceProviderDTOreq;
import com.diplomski.bookingkidsparty.app.dto.response.TypeOfServiceProviderDTOres;
import com.diplomski.bookingkidsparty.app.model.TypeOfServiceProvider;


@Component
public class TypeOfServiceMapper {
	
		public TypeOfServiceProvider convertTypeOfServiceDTOtoTypeOfService(TypeOfServiceProviderDTOreq typeOfServiceDTO) throws Exception {
			TypeOfServiceProvider typeOfServiceProvider = new TypeOfServiceProvider();
			typeOfServiceProvider.setName(typeOfServiceDTO.getName());
				
			return typeOfServiceProvider;
		}

		public TypeOfServiceProviderDTOres convertTypeOfServiceToTypeOfServiceDTO(TypeOfServiceProvider typeOfServiceProvider) {
			TypeOfServiceProviderDTOres typeOfServiceProviderDTO = new TypeOfServiceProviderDTOres(
					typeOfServiceProvider.getId(),
					typeOfServiceProvider.getName()
					);
			return typeOfServiceProviderDTO;
		}
		
		public List<TypeOfServiceProviderDTOres> convertListToListDTO(List<TypeOfServiceProvider> types){
			List<TypeOfServiceProviderDTOres> typesDTO = new ArrayList<TypeOfServiceProviderDTOres>();
			for (TypeOfServiceProvider typeOfServiceProvider : types) {
				typesDTO.add(convertTypeOfServiceToTypeOfServiceDTO(typeOfServiceProvider));
			}
			return typesDTO;
		}
}
