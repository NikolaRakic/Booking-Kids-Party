package com.diplomski.bookingkidsparty.app.util;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.diplomski.bookingkidsparty.app.exceptions.NumberOfPhotoException;


public class FileUploadUtil {
	
	public static void fileCheck(MultipartFile multipartFile, int countOfPhotos) throws IOException {
        try (InputStream input = multipartFile.getInputStream()) {
        	//if multipartFile is an image
                ImageIO.read(input).toString();
        }

        if(countOfPhotos >= 5) {
        	throw new NumberOfPhotoException("Service Provider can't have more than 5 photos");
        }
	}

}
