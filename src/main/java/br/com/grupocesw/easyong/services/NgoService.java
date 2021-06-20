package br.com.grupocesw.easyong.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.request.dtos.NgoRequestDto;
import br.com.grupocesw.easyong.response.dtos.NgoResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoSlimResponseDto;

@Service
public interface NgoService {

	NgoSlimResponseDto create(NgoRequestDto request);
	
	NgoResponseDto retrieve(Long id);

	NgoSlimResponseDto update(Long id, NgoRequestDto request) throws Exception;
	
	Ngo findById(Long id);

	void delete(Long id);

	Page<NgoSlimResponseDto> findByActivated(Pageable pageable);

	Page<NgoSlimResponseDto> findByActivatedWithFilter(String filter, Pageable pageable);

	Page<NgoSlimResponseDto> findSuggested(Pageable pageable);
}
