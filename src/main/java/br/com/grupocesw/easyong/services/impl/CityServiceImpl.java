package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.City;
import br.com.grupocesw.easyong.exceptions.BadRequestException;
import br.com.grupocesw.easyong.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.repositories.CityRepository;
import br.com.grupocesw.easyong.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

	private final CityRepository repository;
	
	@Override
	public City create(City city) {
		return repository.save(city);
	}
	
	@Override
	public City retrieve(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Override
	public City update(Long id, City request) {
		City city = retrieve(id);
		city.setName(request.getName());
		city.setState(request.getState());

		return repository.save(city);
	}

	@Override
	public void delete(Long id) {
		repository.delete(retrieve(id));
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
	public void existsOrThrowsException(Long id) {
		if (!repository.existsById(id))
			throw new BadRequestException("City not found. Id " + id);
	}

}
