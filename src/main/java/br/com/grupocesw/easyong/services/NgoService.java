package br.com.grupocesw.easyong.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.request.dtos.NgoRequestDto;
import br.com.grupocesw.easyong.response.dtos.NgoFullResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoResponseDto;

@Service
public interface NgoService {

	NgoResponseDto create(NgoRequestDto request);
	
	NgoFullResponseDto retrieve(Long id);

	NgoResponseDto update(Long id, NgoRequestDto request) throws Exception;
	
	Ngo findById(Long id);

	void delete(Long id);
	
	Page<NgoFullResponseDto> findByActivatedFull(Pageable pageable);

	Page<NgoFullResponseDto> findByActivatedFullWithFilter(String filter, Pageable pageable);

	Page<NgoResponseDto> findByActivated(Pageable pageable);

	Page<NgoResponseDto> findByActivatedWithFilter(String filter, Pageable pageable);

	Page<NgoResponseDto> findSuggested(Pageable pageable);
}
