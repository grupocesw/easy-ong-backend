package br.com.grupocesw.easyong.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.grupocesw.easyong.request.dtos.PictureRequestDto;
import br.com.grupocesw.easyong.response.dtos.PictureResponseDto;

@Service
public interface PictureService {

	PictureResponseDto create(MultipartFile file);
	
	PictureResponseDto retrieve(Long id);

	PictureResponseDto update(Long id, PictureRequestDto request);

	void delete(Long id);

	List<PictureResponseDto> findAll();
	
    void upload(PictureRequestDto request, MultipartFile file);

    byte[] getPicture(String name) throws IOException;
}
