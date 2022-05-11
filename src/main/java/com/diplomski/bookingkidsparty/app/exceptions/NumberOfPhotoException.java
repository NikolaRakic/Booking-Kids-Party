package com.diplomski.bookingkidsparty.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NumberOfPhotoException extends IndexOutOfBoundsException{
	
	private String messsage;

}
