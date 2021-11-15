package com.diplomski.bookingkidsparty.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diplomski.bookingkidsparty.app.model.ServiceOffer;

@Repository
public interface ServiceOfferRepository extends JpaRepository<ServiceOffer, UUID>{

}
