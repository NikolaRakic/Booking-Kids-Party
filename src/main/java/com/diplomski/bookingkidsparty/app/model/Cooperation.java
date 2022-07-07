package com.diplomski.bookingkidsparty.app.model;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "cooperation")
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Cooperation {
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "playroom_id")
	private ServiceProvider playroom;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cooperationService_id")
	private ServiceProvider cooperationService;

	@Column(name = "is_confirmed", nullable = false)
	private boolean confirmed;
	
	@Column(name = "request_sender")
	private UUID requestSender;
}
