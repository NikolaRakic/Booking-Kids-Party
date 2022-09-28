package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderRequestDTO;
import com.diplomski.bookingkidsparty.app.dto.request.ServiceProviderEditDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderResponseDTO;
import com.diplomski.bookingkidsparty.app.dto.response.ServiceProviderOnePhotoResponseDTO;
import com.diplomski.bookingkidsparty.app.mapper.ServiceProviderMapper;
import com.diplomski.bookingkidsparty.app.model.ServiceProvider;
import com.diplomski.bookingkidsparty.app.model.enums.TypeOfServiceProvider;
import com.diplomski.bookingkidsparty.app.repository.ServiceProviderRepository;
import com.diplomski.bookingkidsparty.app.security.WebSecurityConfig;
import com.diplomski.bookingkidsparty.app.service.EmailSenderService;
import com.diplomski.bookingkidsparty.app.service.ServiceProviderService;
import com.diplomski.bookingkidsparty.app.util.GeneratePassword;

import javassist.NotFoundException;

@Service
@RequiredArgsConstructor
public class ServiceProviderServiceImpl implements ServiceProviderService {

	private final ServiceProviderRepository serviceProviderRepository;
	private final ServiceProviderMapper serviceMapper;
	private final EmailSenderService emailSender;
	private final WebSecurityConfig configuration;

	@Override
	public ServiceProviderResponseDTO add(ServiceProviderRequestDTO serviceProviderDTO) {
		ServiceProvider serviceProvider = serviceMapper.dtoReqToEntity(serviceProviderDTO);
		String plainPassword = GeneratePassword.generete();
		emailSender.sendPasswordOnMail(serviceProvider.getEmail(), plainPassword);
		serviceProvider.setPassword(configuration.passwordEncoder().encode(plainPassword));
		return serviceMapper.entityToDTOres(serviceProviderRepository.saveAndFlush(serviceProvider));
	}

	@Override
	public List<ServiceProviderOnePhotoResponseDTO> findAll() {
		return serviceMapper.listToListDTO(serviceProviderRepository.findAll());
	}

	@Override
	public ServiceProviderResponseDTO findById(UUID id) {
		ServiceProvider serviceProvider = getServiceProvider(id);
		return serviceMapper.entityToDTOres(serviceProvider);
	}

	@Override
	public void delete(UUID id) {
		ServiceProvider serviceProvider = getServiceProvider(id);
		serviceProviderRepository.delete(serviceProvider);
	}

	@Override
	public ServiceProviderResponseDTO edit(UUID id, ServiceProviderEditDTO serviceProviderDTO) {
		ServiceProvider serviceProvider = getServiceProvider(id);
		serviceProvider.setUsername(serviceProviderDTO.getUsername());
		serviceProvider.setAccountNumber(serviceProviderDTO.getAccountNumber());
		serviceProvider.setAdress(serviceProviderDTO.getAdress());
		serviceProvider.setCity(serviceProviderDTO.getCity());
		serviceProvider.setStartOfWork(serviceProviderDTO.getStartOfWork());
		serviceProvider.setEndOfWork(serviceProviderDTO.getEndOfWork());
		// serviceProviderForEdit.setEmail(serviceProviderDTO.getEmail());
		serviceProvider.setMaxNumberOfKids(serviceProviderDTO.getMaxNumberOfKids());
		// serviceProviderForEdit.setPib(serviceProviderDTO.getPib());
		serviceProvider.setTelephoneNumber(serviceProviderDTO.getTelephoneNumber());
		// serviceProviderForEdit.setTypeOfServiceProvider(TypeOfServiceProvider.valueOf(serviceProviderDTO.getTypeOfServiceProvider()));

		return serviceMapper.entityToDTOres(serviceProviderRepository.saveAndFlush(serviceProvider));
	}

	@Override
	public List<ServiceProviderOnePhotoResponseDTO> findAllByType(TypeOfServiceProvider typeOfServiceProvider) {
		List<ServiceProvider> services = serviceProviderRepository
				.findAllByTypeOfServiceProvider(typeOfServiceProvider);
		return serviceMapper.listToListDTO(services);
	}

	@Override
	public String getType(UUID id){
		return findById(id).getTypeOfServiceProvider();
	}

	private ServiceProvider getServiceProvider(UUID id) {
		return serviceProviderRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Service Provider with id " + id + " dosen't exist."));
	}

}
