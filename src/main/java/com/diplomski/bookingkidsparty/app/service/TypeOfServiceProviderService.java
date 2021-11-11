package com.diplomski.bookingkidsparty.app.service;

import java.util.List;

import com.diplomski.bookingkidsparty.app.model.TypeOfServiceProvider;

public interface TypeOfServiceProviderService {

	String add(TypeOfServiceProvider request);

	List<TypeOfServiceProvider> getAll();

}
