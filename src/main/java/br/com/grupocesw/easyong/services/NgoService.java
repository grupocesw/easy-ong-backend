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

	public NgoResponseDto create(NgoRequestDto request);
	
	public NgoFullResponseDto retrieve(Long id);

	public NgoResponseDto update(Long id, NgoRequestDto request) throws Exception;
	
	public Ngo findById(Long id);

	public void delete(Long id);
	
	public Page<NgoFullResponseDto> findByActivatedFull(String filter, Pageable pageable);

	public Page<NgoResponseDto> findByActivated(String filter, Pageable pageable);

	public Page<NgoResponseDto> findSuggested(Pageable pageable);
}
