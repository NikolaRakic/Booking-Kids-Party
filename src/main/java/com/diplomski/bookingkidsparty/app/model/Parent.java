package com.diplomski.bookingkidsparty.app.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "parent")
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Parent extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UUID id;
	
	private String name;
	
	private String surname;
	

}
