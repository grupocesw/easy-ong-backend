package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.City;
import br.com.grupocesw.easyong.exceptions.BadRequestException;
import br.com.grupocesw.easyong.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.repositories.CityRepository;
import br.com.grupocesw.easyong.services.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {

	private final CityRepository repository;
	
	@Override
	@CacheEvict(value = "cities", allEntries = true)
	public City create(City request) {
		log.info("Create city with name {}", request.getName());

		return repository.save(request);
	}
	
	@Override
	@Cacheable(value = "cities", key = "#id")
	public City retrieve(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new BadRequestException("City", id));
	}

	@Override
	@CacheEvict(value = "cities", allEntries = true)
	public City update(Long id, City request) {
		log.info("Update city with name {}", request.getName());
		City city = retrieve(id);
		city.setName(request.getName());
		city.setState(request.getState());

		return repository.save(city);
	}

	@Override
	@CacheEvict(value = "cities", allEntries = true)
	public void delete(Long id) {
		log.info("Delete city with id {}", id);

		repository.delete(retrieve(id));
	}
	
	@Override
	public List<City> findAll() {
		return repository.findAll();
	}

	@Override
	@Cacheable(value = "cities", key = "#pageable")
	public Page<City> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public Page<City> findByName(String name, Pageable pageable) {
		return repository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public void existsOrThrowsException(Long id) {
		if (!repository.existsById(id))
			throw new BadRequestException("City not found. Id " + id);
	}

}
