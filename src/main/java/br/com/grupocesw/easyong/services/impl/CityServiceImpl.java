package br.com.grupocesw.easyong.services.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.City;
import br.com.grupocesw.easyong.repositories.CityRepository;
import br.com.grupocesw.easyong.services.CityService;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

	private CityRepository repository;
	
	@Override
	public City create(City request) {
		try {
			City city = City.builder()
				.name(request.getName())
				.state(request.getState())
				.build();
			
			return repository.save(city);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	@Override
	public City retrieve(Long id) {
		Optional<City> optional = repository.findById(id);
		optional.orElseThrow(() -> new ResourceNotFoundException(id));

		return optional.get();
	}

	@Override
	public City update(Long id, City request) {
		try {
			Optional<City> optional = repository.findById(id);
			optional.orElseThrow(() -> new ResourceNotFoundException(id));
			
			City city = optional.get();			
			city.setName(request.getName());
			city.setState(request.getState());

			return repository.save(city);
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
	public List<City> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<City> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public Page<City> findByName(String name, Pageable pageable) {
		return repository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public Optional<City> findById(Long Id) {
		return repository.findById(Id);
	}

}
