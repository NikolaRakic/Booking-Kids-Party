package com.diplomski.bookingkidsparty.app.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Cooperation {
	
	private UUID id;
	
	private Set<ServiceProvider> playRoom;
	
	private Set<ServiceProvider> cooperationService;

}
