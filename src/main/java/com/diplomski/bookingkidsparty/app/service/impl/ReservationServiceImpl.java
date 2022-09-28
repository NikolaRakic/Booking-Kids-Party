package com.diplomski.bookingkidsparty.app.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import com.diplomski.bookingkidsparty.app.exceptions.AccessException;
import com.diplomski.bookingkidsparty.app.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.ReservationRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.response.PageableResponse;
import com.diplomski.bookingkidsparty.app.dto.response.ReservationResponseDTO;
import com.diplomski.bookingkidsparty.app.mapper.ReservationMapper;
import com.diplomski.bookingkidsparty.app.model.Reservation;
import com.diplomski.bookingkidsparty.app.model.ServiceOffer;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.ReservationRepository;
import com.diplomski.bookingkidsparty.app.service.EmailSenderService;
import com.diplomski.bookingkidsparty.app.service.ReservationService;
import com.diplomski.bookingkidsparty.app.util.ReservationValidate;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final ReservationRepository reservationRepository;
	private final ReservationMapper reservationMapper;
	private final ReservationValidate reservationValidate;
	private final EmailSenderService emailSenderService;

	@Transactional(rollbackOn = Exception.class)
	@Override
	public UUID add(ReservationRequestDTO reservationDTOreq) {
		List<Reservation> newReservations = reservationMapper.dtoToEntity(reservationDTOreq);
		Reservation playroom = newReservations.stream()
				.filter(sf -> sf.getServiceOffer().getServiceProvider()
						.getTypeOfServiceProvider() == TypeOfServiceProvider.PLAYROOM)
				.findFirst().orElseThrow(() -> new IllegalArgumentException("Playroom must exists!"));
		reservationValidate.requestValidation(playroom);
		reservationRepository.save(playroom);
		newReservations.remove(playroom);
		for (Reservation reservation : newReservations) {
			reservationValidate.requestValidation(reservation);
			reservationRepository.save(reservation);
			emailSenderService.sendConfirmReservationOnMail(reservationDTOreq.getUsersEmail(), playroom, newReservations);
		}
		return playroom.getId();
	}

	@Override
	public PageableResponse getAll(Pageable pageable) {
		Page<Reservation> reservationsPage = reservationRepository.findAll(pageable);
		HttpHeaders headers = new HttpHeaders();
        headers.set("total", String.valueOf(reservationsPage.getTotalPages()));

        List<ReservationResponseDTO> reservationsDTO = reservationMapper.listToListDTO(reservationsPage.getContent());
		return new PageableResponse(reservationsDTO, headers);
	}

	@Override
	public List<ReservationResponseDTO> getAllByParty(UUID playRoomId, LocalDate dateOfReservation,
			LocalTime startTime) {
		List<Reservation> reservations = reservationRepository
				.findAllByPlayroomIdAndDateOfReservationAndStartTime(playRoomId, dateOfReservation, startTime);
		return reservationMapper.listToListDTO(reservations);
	}

	@Override
	public PageableResponse getAllByServisProvider(UUID serviceProviderId, int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("dateOfReservation").descending());
		Page<Reservation> reservationsPage = reservationRepository.findAllByServiceOfferServiceProviderId(serviceProviderId, paging);
		HttpHeaders headers = new HttpHeaders();
        headers.set("total", String.valueOf(reservationsPage.getTotalPages()));
        reservationsPage.getContent().forEach(reservation -> reservationMapper.entityToDTO(reservation));
        
        List<ReservationResponseDTO> reservationsDTO = reservationMapper.listToListDTO(reservationsPage.getContent());
		return new PageableResponse(reservationsDTO, headers);
	}

	@Override
	public PageableResponse getAllByUser(UUID userId, Pageable pageable) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(!userId.equals(currentUser.getId())) {
			throw new AccessException("Only a logged-in user can get his reservations");
		}
		Page<Reservation> reservationsPage = reservationRepository.findAllByUserId(userId, pageable);
		HttpHeaders headers = new HttpHeaders();
        headers.set("total", String.valueOf(reservationsPage.getTotalPages()));
     
        List<ReservationResponseDTO> reservationsDTO = reservationMapper.listToListDTO(reservationsPage.getContent());
		return new PageableResponse(reservationsDTO, headers);
	}

	@Override
	public boolean delete(UUID id) {
		Optional<Reservation> reservationOptional = reservationRepository.findById(id);
		if (reservationOptional.isPresent()) {
			reservationRepository.delete(reservationOptional.get());
			return true;
		}
		return false;
	}

}
