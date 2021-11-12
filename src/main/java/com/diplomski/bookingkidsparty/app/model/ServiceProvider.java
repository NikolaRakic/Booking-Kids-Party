package com.diplomski.bookingkidsparty.app.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Table(name = "service_provider")
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ServiceProvider {
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;
	
	private String name;
	
	@Column(name = "account_number")
	private String accountNumber;
	
	private String email;
	
	private String password;
	
	private String pib;
	
	@Column(name = "max_number_of_kids")
	private int maxNumberOfKids;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_of_service_provider_id")
	private TypeOfServiceProvider typeOfServiceProvider;
	
	private String city;
	
	private String adress;
	
	@Column(name = "telephone_number")
	private String telephoneNumber;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "serviceProvider")
	private Set<Photo> photos;
	
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "serviceProvider")
	private Set<ServiceOffer> serviceOffers;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "serviceProvider")
	private Set<Evaluation> evalutions;
	
	@ManyToMany
	@JoinTable(name = "cooperation", joinColumns = @JoinColumn(name = "play_room_id"), inverseJoinColumns = @JoinColumn(name="cooperation_service_id"))
	private Set<ServiceProvider> playRoom;
	
	@ManyToMany
	@JoinTable(name = "cooperation", joinColumns = @JoinColumn(name = "cooperation_service_id"), inverseJoinColumns = @JoinColumn(name="play_room_id"))
	private Set<ServiceProvider> cooperationService;

	
}
