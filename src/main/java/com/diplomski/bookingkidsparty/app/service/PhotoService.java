package com.diplomski.bookingkidsparty.app.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

	UUID add(MultipartFile multipartFile, UUID serviceProviderId) throws IOException, Exception;

	boolean delete(UUID photoId);

}
