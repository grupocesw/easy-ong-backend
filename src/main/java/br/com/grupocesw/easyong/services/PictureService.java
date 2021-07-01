package br.com.grupocesw.easyong.services;

import java.io.IOException;
import java.util.List;

import br.com.grupocesw.easyong.entities.Picture;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.grupocesw.easyong.request.dtos.PictureRequestDto;
import br.com.grupocesw.easyong.response.dtos.PictureResponseDto;

@Service
public interface PictureService {

	Picture create(MultipartFile file);

	Picture retrieve(Long id);

	Picture update(Long id, Picture request);

	void delete(Long id);

	void existsOrThrowsException(Long id);

	List<Picture> findAll();
	
    void upload(Picture request, MultipartFile file);

    byte[] getPicture(String name) throws IOException;
}
