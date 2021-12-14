package com.diplomski.bookingkidsparty.app.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "type_of_service_provider")
@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class TypeOfServiceProvider {
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;
	
	private String name;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "typeOfServiceProvider")
	@JsonIgnore
	private Set<ServiceProvider> serviceProviders;

	public TypeOfServiceProvider(UUID id, String name) throws Exception {
		super();
		if(name == "" || name == null) {
			throw new Exception("The name of the service provider type is incorrect!");
		}
		this.id = id;
		this.name = name;
	}
	
	
}

