package br.com.grupocesw.easyong.services;

import org.springframework.stereotype.Service;

@Service
public interface StreetService {

	public Boolean existsById(Long id);
}
