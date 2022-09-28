package com.diplomski.bookingkidsparty.app.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.diplomski.bookingkidsparty.app.exceptions.AccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.diplomski.bookingkidsparty.app.dto.response.PhotoResponseDTO;
import com.diplomski.bookingkidsparty.app.mapper.PhotoMapper;
import com.diplomski.bookingkidsparty.app.model.Photo;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.User;
import com.diplomski.bookingkidsparty.app.repository.PhotoRepository;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.service.PhotoService;
import com.diplomski.bookingkidsparty.app.util.FileUploadUtil;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

	private final PhotoRepository photoRepository;
	private final ServiceProviderRepository serviceProviderRepository;
	private final PhotoMapper photoMapper;

	@Override
	public PhotoResponseDTO add(MultipartFile multipartFile, UUID serviceProviderId) throws IOException {

		ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceProviderId)
				.orElseThrow(() -> new IllegalArgumentException());
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(!serviceProvider.getId().equals(currentUser.getId())) {
			throw new AccessException("Only a logged-in service provider can add photo for himself");
		}
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		int countPhotosByServiceProvider = photoRepository.getCountOfPhotosByServiceProvider(serviceProvider);
		FileUploadUtil.fileCheck(multipartFile, countPhotosByServiceProvider);

		Photo photo = new Photo();
		photo.setName(fileName);
		photo.setServiceProvider(serviceProvider);
		photo.setData(multipartFile.getBytes());
		photoRepository.save(photo);

		return photoMapper.entityToDto(photo);
	}

	@Override
	public boolean delete(UUID photoId) {
		Optional<Photo> photoOptional = photoRepository.findById(photoId);
		if (photoOptional.isPresent()) {
			photoRepository.deleteById(photoId);
			return true;
		}
		return false;
	}

	@Override
	public List<PhotoResponseDTO> getPhotos(UUID serviceProviderId) {
		serviceProviderRepository.findById(serviceProviderId).orElseThrow(() -> new IllegalArgumentException(
				"Service provider with id " + serviceProviderId + " doesn't exist."));
		List<Photo> photos = photoRepository.findAllByServiceProviderId(serviceProviderId);
		return photoMapper.listToListDTO(photos);
	}

}
