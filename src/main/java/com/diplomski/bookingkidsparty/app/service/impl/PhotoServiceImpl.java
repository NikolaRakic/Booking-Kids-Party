package com.diplomski.bookingkidsparty.app.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.diplomski.bookingkidsparty.app.model.Photo;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.PhotoRepository;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.service.PhotoService;
import com.diplomski.bookingkidsparty.app.util.FileUploadUtil;

@Service
public class PhotoServiceImpl implements PhotoService{

	@Autowired
	PhotoRepository photoRepository;
	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	
	@Override
	public UUID add(MultipartFile multipartFile, UUID serviceProviderId) throws Exception {
		
		Optional<ServiceProvider> serviceProviderOptional = serviceProviderRepository.findById(serviceProviderId);
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String uploadDir = "../../../Documents/Projects/booking-kids-parties-front/src/assets/provider-photos/" + serviceProviderId;

		if(serviceProviderOptional.isPresent()) {
		     FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		     
			 Photo photo = new Photo();
		     photo.setName(fileName);
		     photo.setServiceProvider(serviceProviderOptional.get());
		     
		     photo.setPath(uploadDir);
		     photoRepository.save(photo);
		     
		     return photo.getId();
		} 
        throw new Exception("Service with this id doesn't exists");
	}

	@Override
	public boolean delete(UUID photoId) {
		Optional<Photo> photoOptional = photoRepository.findById(photoId);
		if(photoOptional.isPresent()) {
			String path = photoOptional.get().getPath() + "/" + photoOptional.get().getName();
			 try {
				Files.deleteIfExists(Paths.get(path));
				photoRepository.deleteById(photoId);
				 return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	

}
