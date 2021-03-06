package com.diplomski.bookingkidsparty.app.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.ToString;

@Table(name = "service_offer")
@Entity
@Data
@ToString
public class ServiceOffer {
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "end_date")
	private LocalDate endDate;
	
	@Column(name = "max_number_of_kids")
	private int maxNumberOfKids;
	
	@Column(name = "max_number_of_adults")
	private int maxNumberOfAdults;
	
	@Column(name = "price_per_kid")
	private int pricePerKid;
	
	@Column(name = "price_per_adult")
	private int pricePerAdult;
	
	@Column(name = "price_per_hour")
	private int pricePerHour;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_provider_id")
	private ServiceProvider serviceProvider;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "serviceOffer")
	private Set<Reservation> reservations;
}
