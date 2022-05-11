package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.response.PhotoDTOres;
import com.diplomski.bookingkidsparty.app.model.Photo;

@Component
public class PhotoMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public PhotoDTOres entityToDto(Photo photo) {
		return modelMapper.map(photo, PhotoDTOres.class);
	}
	
	public List<PhotoDTOres> listToListDTO(List<Photo> photos){
		List<PhotoDTOres> photosDto = new ArrayList<>();
		for (Photo photo : photos) {
			photosDto.add(entityToDto(photo));
		}
		return photosDto;
	}
}
