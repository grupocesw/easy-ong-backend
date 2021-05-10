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

	public CityResponseDto create(CityRequestDto request);

	public CityResponseDto retrieve(Long id);

	public CityResponseDto update(Long id, CityRequestDto request);

	public void delete(Long id);
	
	public List<CityResponseDto> findAll();
	
	public Page<CityResponseDto> findAll(Pageable pageable);
	
	public Page<CityResponseDto> findByName(String name, Pageable pageable);
	
	public Optional<City> findById(Long Id);
}
