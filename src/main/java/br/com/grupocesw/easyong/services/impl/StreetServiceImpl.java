package br.com.grupocesw.easyong.services.impl;

import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.repositories.StreetRepository;
import br.com.grupocesw.easyong.request.dtos.StreetCreateRequestDto;
import br.com.grupocesw.easyong.response.dtos.StreetResponseDto;
import br.com.grupocesw.easyong.services.StreetService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StreetServiceImpl implements StreetService {

	private StreetRepository repository;
	
	@Override
	public Boolean existsById(Long id) {
		return repository.existsById(id);
	}

	@Override
	public StreetResponseDto create(StreetCreateRequestDto streetDto) {
		return new StreetResponseDto(repository.save(streetDto.build()));
	}

}
