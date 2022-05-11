package com.diplomski.bookingkidsparty.app.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.diplomski.bookingkidsparty.app.dto.response.PhotoDTOres;
import com.diplomski.bookingkidsparty.app.model.Photo;

public interface PhotoService {

	PhotoDTOres add(MultipartFile multipartFile, UUID serviceProviderId) throws IOException;

	boolean delete(UUID photoId);

	List<PhotoDTOres> getPhotos(UUID serviceProviderId);

}
