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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.diplomski.bookingkidsparty.app.util.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "user")
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	private String name;
	
	private String surname;
	
	private String email;
	
	private String username;
	
	private String password;
	
	@Column(name = "telephone_number")
	private String telephoneNumber;
	
	private boolean blocked;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Reservation> reservations;
	
	@JoinColumn(name = "user_role")
	private Role userRole;
}
