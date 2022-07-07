package com.diplomski.bookingkidsparty.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderOnePhotoResponseDTO;
import com.diplomski.bookingkidsparty.app.model.Cooperation;

@Repository
public interface CooperationRepository extends JpaRepository<Cooperation, UUID>{

	Optional<Cooperation> findByPlayroomIdAndCooperationServiceId(UUID playroomId, UUID cooperationServiceId);

	List<Cooperation> findAllByPlayroomId(UUID id);

	List<Cooperation> findAllByCooperationServiceId(UUID id);
}
