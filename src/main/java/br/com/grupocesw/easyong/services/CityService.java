package br.com.grupocesw.easyong.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.City;
import br.com.grupocesw.easyong.request.dtos.CityRequestDto;
import br.com.grupocesw.easyong.response.dtos.CityResponseDto;

@Service
public interface CityService {

	CityResponseDto create(CityRequestDto request);

	CityResponseDto retrieve(Long id);

	CityResponseDto update(Long id, CityRequestDto request);

	void delete(Long id);
	
	List<CityResponseDto> findAll();
	
	Page<CityResponseDto> findAll(Pageable pageable);
	
	Page<CityResponseDto> findByName(String name, Pageable pageable);
	
	Optional<City> findById(Long Id);
}
