package br.com.grupocesw.easyong.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.City;

@Service
public interface CityService {

	City create(City request);

	City retrieve(Long id);

	City update(Long id, City request);

	void delete(Long id);
	
	List<City> findAll();
	
	Page<City> findAll(Pageable pageable);
	
	Page<City> findByName(String name, Pageable pageable);
	
	Optional<City> findById(Long Id);
}
