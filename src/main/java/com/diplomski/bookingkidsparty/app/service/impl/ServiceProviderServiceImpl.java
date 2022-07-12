package com.diplomski.bookingkidsparty.app.service.impl;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

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
public class ServiceProviderServiceImpl implements ServiceProviderService {

	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	@Autowired
	ServiceProviderMapper serviceMapper;
	@Autowired
	EmailSenderService emailSender;
	@Autowired
	GeneratePassword generatePassword;
	@Autowired
	WebSecurityConfig configuration;

	@Override
	public UUID add(ServiceProviderRequestDTO serviceProviderDTO) throws NotFoundException, MessagingException {
		ServiceProvider serviceProvider = serviceMapper.dtoReqToEntity(serviceProviderDTO);
		String plainPassword = generatePassword.generete();
		emailSender.sendPasswordOnMail(serviceProvider.getEmail(), plainPassword);
		serviceProvider.setPassword(configuration.passwordEncoder().encode(plainPassword));
		serviceProviderRepository.saveAndFlush(serviceProvider);
		return serviceProvider.getId();
	}

	@Override
	public List<ServiceProviderOnePhotoResponseDTO> findAll() {
		List<ServiceProvider> services = serviceProviderRepository.findAll();
		return serviceMapper.listToListDTO(services);
	}

	@Override
	public ServiceProviderResponseDTO findById(UUID id) {
		ServiceProvider serviceProvider = getServiceProvider(id);
		ServiceProviderResponseDTO serviceProviderDTO = serviceMapper.entityToDTOres(serviceProvider);
		return serviceProviderDTO;
	}

	@Override
	public void delete(UUID id) {
		ServiceProvider serviceProvider = getServiceProvider(id);
		serviceProviderRepository.delete(serviceProvider);
	}

	@Override
	public void edit(UUID id, ServiceProviderEditDTO serviceProviderDTO) {
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

		serviceProviderRepository.saveAndFlush(serviceProvider);
	}

	@Override
	public List<ServiceProviderOnePhotoResponseDTO> findAllByType(TypeOfServiceProvider typeOfServiceProvider) {
		List<ServiceProvider> services = serviceProviderRepository
				.findAllByTypeOfServiceProvider(typeOfServiceProvider);
		return serviceMapper.listToListDTO(services);
	}

	@Override
	public String getType(UUID id) throws Exception {
		return findById(id).getTypeOfServiceProvider();
	}

	private ServiceProvider getServiceProvider(UUID id) {
		return serviceProviderRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Service Provider with id " + id + " dosen't exist."));
	}

}
