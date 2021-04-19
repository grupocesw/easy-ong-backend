package br.com.grupocesw.easyong.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Ngo;

@Service
public interface NgoService {

	public Page<Ngo> findByActivated(Pageable pageable);

	public Page<Ngo> findSuggested(Pageable pageable);

	public Ngo create(Ngo ngo);
	
	public Ngo retrieve(Long id);

	public Ngo update(Long id, Ngo payload) throws Exception;

	public void delete(Long id);
}
