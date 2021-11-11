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
//public class Cooperation {
//	
//	@Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//        name = "UUID",
//        strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @Column(name = "id", updatable = false, nullable = false)
//	private UUID id;
//	
//	@ManyToMany(mappedBy = "cooperations")
//	@Column(name = "play_room_id")
//	private Set<ServiceProvider> playRoom;
//	
//	@ManyToMany(mappedBy = "cooperations")
//	@Column(name = "cooperation_service_id")
//	private Set<ServiceProvider> cooperationService;
//
//}
