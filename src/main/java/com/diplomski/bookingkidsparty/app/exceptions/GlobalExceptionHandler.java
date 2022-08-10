package com.diplomski.bookingkidsparty.app.exceptions;

import java.io.IOException;

import ch.qos.logback.core.CoreConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.xml.ws.Response;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NumberOfPhotoException.class)
	public ResponseEntity<String> numberOfPhotoExceptionHandler(NumberOfPhotoException e){
		return new ResponseEntity<String>(e.getMesssage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<String> ioExceptionHandler(IOException e){
		return new ResponseEntity<String>("This is not an image (only BMP, GIF, JPG and PNG are allowed)", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException e){
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessException.class)
	public ResponseEntity<?> deniedAccess(AccessException e){
		return new ResponseEntity<>(e.getMesssage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> notFoundHandler(EntityNotFoundException e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
}
