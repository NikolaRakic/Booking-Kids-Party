package com.diplomski.bookingkidsparty.app.model;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Table(name = "service_provider")
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ServiceProvider extends User{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Column(name = "account_number")
	private String accountNumber;
	
	private String pib;
	
	@Column(name = "start_of_work")
	private LocalTime startOfWork;
	
	@Column(name = "end_of_work")
	private LocalTime endOfWork;
	
	@Column(name = "max_number_of_kids")
	private int maxNumberOfKids;
	
	@Column(name = "type_of_service_provider")
	@Enumerated(EnumType.STRING)
	private TypeOfServiceProvider typeOfServiceProvider;
	
	private String city;
	
	private String adress;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "serviceProvider")
	private Set<Photo> photos;
	
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "serviceProvider")
	private Set<ServiceOffer> serviceOffers;
	
//	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "serviceProvider")
//	private Set<Evaluation> evalutions;
	
	@ManyToMany
	@JoinTable(name = "cooperation", joinColumns = @JoinColumn(name = "play_room_id"), inverseJoinColumns = @JoinColumn(name="cooperation_service_id"))
	private Set<ServiceProvider> playRoom;
	
	@ManyToMany
	@JoinTable(name = "cooperation", joinColumns = @JoinColumn(name = "cooperation_service_id"), inverseJoinColumns = @JoinColumn(name="play_room_id"))
	private Set<ServiceProvider> cooperationService;
	
}
