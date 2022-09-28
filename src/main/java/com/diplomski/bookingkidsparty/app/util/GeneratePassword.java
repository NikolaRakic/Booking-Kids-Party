package com.diplomski.bookingkidsparty.app.util;

import org.passay.CharacterRule;
import org.passay.CharacterData;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Component;

public class GeneratePassword {
	
	public static String generete() {
		PasswordGenerator gen = new PasswordGenerator();
		  CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
		  CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);  
		  
		  CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
		  CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
		  
		  CharacterData digitChars = EnglishCharacterData.Digit;
		  CharacterRule digitRule = new CharacterRule(digitChars);
		  
		  String password = gen.generatePassword(8, lowerCaseRule, 
			      upperCaseRule, digitRule);
		  return password;
	}
	
}
