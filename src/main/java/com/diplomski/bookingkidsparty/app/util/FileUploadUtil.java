package com.diplomski.bookingkidsparty.app.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import javax.imageio.ImageIO;


import org.springframework.web.multipart.MultipartFile;


public class FileUploadUtil {

	public static void saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile) throws Exception {
        Path uploadPath = Paths.get(uploadDir);
        
        fileCheck(multipartFile, uploadDir, uploadPath, fileName);
         
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }      
    }
	
	
	public static void fileCheck(MultipartFile multipartFile, String uploadDir, Path uploadPath, String fileName) throws Exception {
        try (InputStream input = multipartFile.getInputStream()) {
        	//if multipartFile is an image
            try {
                ImageIO.read(input).toString();
            } catch (Exception e) {
            	throw new Exception("This is not an image (only BMP, GIF, JPG and PNG are allowed)."); 
            }
        }
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }else {
        	  //images counter
            File f = new File(uploadDir); 
            int count = 0;
            for (File file : f.listFiles()) {
            	
            	if(file.getName().equals(fileName)) {
            		throw new Exception("Photo with name \" " + fileName +" \" arleady exists"); 
            	}
                if (file.isFile()) {
                    count++;
                }
            }
            if(count >= 5) {
            	throw new Exception("This provider already has 5 images"); 
            }
        }
	}

}
