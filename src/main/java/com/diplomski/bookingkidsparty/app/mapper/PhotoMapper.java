package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.response.PhotoResponseDTO;
import com.diplomski.bookingkidsparty.app.model.Photo;

@Component
public class PhotoMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public PhotoResponseDTO entityToDto(Photo photo) {
		return modelMapper.map(photo, PhotoResponseDTO.class);
	}
	
	public List<PhotoResponseDTO> listToListDTO(List<Photo> photos){
		List<PhotoResponseDTO> photosDto = new ArrayList<>();
		for (Photo photo : photos) {
			photosDto.add(entityToDto(photo));
		}
		return photosDto;
	}
}
