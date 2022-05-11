package com.diplomski.bookingkidsparty.app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.diplomski.bookingkidsparty.app.dto.response.PhotoDTOres;
import com.diplomski.bookingkidsparty.app.service.PhotoService;

@RestController
@RequestMapping("/photos")
public class PhotoController {

	@Autowired
	PhotoService photoService;

	@PostMapping("/{serviceProviderId}/upload")
	public ResponseEntity<PhotoDTOres> savePhoto(@RequestParam("image") MultipartFile multipartFile,
			@PathVariable("serviceProviderId") UUID serviceProviderId) throws Exception {
		PhotoDTOres photoDto = photoService.add(multipartFile, serviceProviderId);

		return new ResponseEntity<PhotoDTOres>(photoDto, HttpStatus.OK);
	}

	@GetMapping("/{serviceProviderId}")
	public ResponseEntity<List<PhotoDTOres>> getPhotosByServiceProviderId(
			@PathVariable("serviceProviderId") UUID serviceProviderId) {
		List<PhotoDTOres> photos = photoService.getPhotos(serviceProviderId);
		return new ResponseEntity<>(photos, HttpStatus.OK);
	}

	@DeleteMapping("/{photoId}")
	public ResponseEntity<?> delete(@PathVariable("photoId") UUID photoId) throws Exception {
		return new ResponseEntity<>(photoService.delete(photoId) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
	}

}
