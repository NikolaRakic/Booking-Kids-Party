package com.diplomski.bookingkidsparty.app.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.diplomski.bookingkidsparty.app.service.PhotoService;



@Controller
public class PhotoController {

	@Autowired
	PhotoService photoService;
	
	@PostMapping("/photo/{serviceProviderId}")
    public ResponseEntity<UUID> savePhoto(@RequestParam("image") MultipartFile multipartFile, @PathVariable("serviceProviderId") UUID serviceProviderId) throws Exception {
        UUID photoId = photoService.add(multipartFile, serviceProviderId);
        
        return new ResponseEntity<UUID>(photoId, HttpStatus.OK);
    }
	
	@DeleteMapping("/photo/{photoId}")
	public ResponseEntity<?> delete(@PathVariable("photoId") UUID photoId) throws Exception{
		return new ResponseEntity<>(photoService.delete(photoId) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
	}
	
}
