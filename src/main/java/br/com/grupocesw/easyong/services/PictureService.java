package br.com.grupocesw.easyong.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.grupocesw.easyong.request.dtos.PictureRequestDto;
import br.com.grupocesw.easyong.response.dtos.PictureResponseDto;

@Service
public interface PictureService {

	public PictureResponseDto create(MultipartFile file);
	
	public PictureResponseDto retrieve(Long id);

	public PictureResponseDto update(Long id, PictureRequestDto request);

	public void delete(Long id);	

	public List<PictureResponseDto> findAll();
	
    public void upload(PictureRequestDto request, MultipartFile file);

    public byte[] getPicture(String name) throws IOException;
}
