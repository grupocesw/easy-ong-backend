package br.com.grupocesw.easyong.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.response.dtos.NgoSlimResponseDto;

@Service
public interface NgoService {

	Ngo create(Ngo request);

	Ngo retrieve(Long id);

	Ngo update(Long id, Ngo request) throws Exception;

	void delete(Long id);

	void existsOrThrowsException(Long id);

	Page<Ngo> findByActivated(Pageable pageable);

	Page<Ngo> findByActivatedWithFilter(String filter, Pageable pageable);

	Page<Ngo> findSuggested(Pageable pageable);

	void favorite(Long ngoId);

	Page<Ngo> getFavoriteNgos(Pageable pageable);
}
