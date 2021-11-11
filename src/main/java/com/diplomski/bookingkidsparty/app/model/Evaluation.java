package com.diplomski.bookingkidsparty.app.model;

import java.time.LocalDateTime;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Evaluation")
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Evaluation {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	private LocalDateTime dateTime;
	
	private String comment;
	
	//private Reservation reservation;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_provider_id")
	private ServiceProvider serviceProvider;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Reservation reservation;
}
