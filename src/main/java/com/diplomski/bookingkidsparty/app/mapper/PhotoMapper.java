package com.diplomski.bookingkidsparty.app.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diplomski.bookingkidsparty.app.dto.response.PhotoResponseDTO;
import com.diplomski.bookingkidsparty.app.model.Photo;

@Component
@RequiredArgsConstructor
public class PhotoMapper {

	private final ModelMapper modelMapper;
	
	public PhotoResponseDTO entityToDto(Photo photo) {
		return modelMapper.map(photo, PhotoResponseDTO.class);
	}
	
	public Set<PhotoResponseDTO> listToListDTO(Set<Photo> photos){
		Set<PhotoResponseDTO> photosDto = new HashSet<PhotoResponseDTO>();
		photos.forEach(photo -> photosDto.add(entityToDto(photo)));
		return photosDto;
	}

	public List<PhotoResponseDTO> listToListDTO(List<Photo> photos) {
		List<PhotoResponseDTO> photosDto = new ArrayList<PhotoResponseDTO>();
		photos.forEach(photo -> photosDto.add(entityToDto(photo)));
		return photosDto;
	}
}
