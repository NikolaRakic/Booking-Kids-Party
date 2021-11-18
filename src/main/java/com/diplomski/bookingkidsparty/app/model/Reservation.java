package com.diplomski.bookingkidsparty.app.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import javax.persistence.CascadeType;
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

@Table(name = "Reservation")
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Reservation {
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Column(name = "date_of_reservation")
	private LocalDate dateOfReservation;
	
	@Column(name = "start_time")
	private LocalTime startTime;
	
	@Column(name = "end_time")
	private LocalTime endTime;
	
	@Column(name = "number_of_kids")
	private int numberOfKids;
	
	@Column(name = "number_of_adults")
	private int numberOfAdults;
	
	@Column(name = "additional_requirements")
	private String additionalRequirements;
	
	@Column(name = "age_of_kid")
	private int ageOfKid;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_offer_id")
	private ServiceOffer serviceOffer;
	
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "reservation")
	private Rating evaluation;

}
