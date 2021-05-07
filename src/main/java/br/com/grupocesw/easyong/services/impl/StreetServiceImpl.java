package br.com.grupocesw.easyong.services.impl;

import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.repositories.StreetRepository;
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

}
