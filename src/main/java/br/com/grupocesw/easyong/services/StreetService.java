package br.com.grupocesw.easyong.services;

import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.request.dtos.StreetCreateRequestDto;
import br.com.grupocesw.easyong.response.dtos.StreetResponseDto;

@Service
public interface StreetService {

	public Boolean existsById(Long id);
	
	public StreetResponseDto create(StreetCreateRequestDto streetDto);
}
