package br.com.grupocesw.easyong.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.City;
import br.com.grupocesw.easyong.repositories.CityRepository;
import br.com.grupocesw.easyong.request.dtos.CityRequestDto;
import br.com.grupocesw.easyong.response.dtos.CityResponseDto;
import br.com.grupocesw.easyong.services.CityService;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

	private CityRepository repository;
	
	@Override
	public CityResponseDto create(CityRequestDto request) {		
		try {
			City city = City.builder()
				.name(request.getName())
				.state(request.getState())
				.build();
			
			return new CityResponseDto(repository.save(city));
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	@Override
	public CityResponseDto retrieve(Long id) {
		Optional<City> optional = repository.findById(id);
		optional.orElseThrow(() -> new ResourceNotFoundException(id));

		return new CityResponseDto(optional.get());
	}

	@Override
	public CityResponseDto update(Long id, CityRequestDto request) {
		try {
			Optional<City> optional = repository.findById(id);
			optional.orElseThrow(() -> new ResourceNotFoundException(id));
			
			City city = optional.get();			
			city.setName(request.getName());
			city.setState(request.getState());

			return new CityResponseDto(repository.save(city));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}		
	}

	@Override
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	@Override
	public List<CityResponseDto> findAll() {		
		List<CityResponseDto> result = repository.findAll()
				.stream()
				.map(obj -> new CityResponseDto(obj))
				.collect(Collectors.toList());
		
		return result;
	}

	@Override
	public Page<CityResponseDto> findAll(Pageable pageable) {		
		Page<City> result = repository.findAll(pageable);
		return result.map(obj -> new CityResponseDto(obj));
	}
	
	@Override
	public Page<CityResponseDto> findByName(String name, Pageable pageable) {
		Page<City> result = repository.findByNameContaining(name, pageable);
		return result.map(obj -> new CityResponseDto(obj));		
	}

	@Override
	public Optional<City> findById(Long Id) {
		return repository.findById(Id);
	}

}
