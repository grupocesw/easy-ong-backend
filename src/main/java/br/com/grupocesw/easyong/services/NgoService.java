package br.com.grupocesw.easyong.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.request.dtos.NgoCreateRequestDto;
import br.com.grupocesw.easyong.request.dtos.NgoUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.NgoFullResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoResponseDto;

@Service
public interface NgoService {
	
	public Page<NgoFullResponseDto> findByActivatedFull(Pageable pageable);

	public Page<NgoResponseDto> findByActivated(Pageable pageable);

	public Page<NgoResponseDto> findSuggested(Pageable pageable);

	public NgoResponseDto create(NgoCreateRequestDto ngoDto);
	
	public NgoFullResponseDto retrieve(Long id);

	public NgoResponseDto update(Long id, NgoUpdateRequestDto ngoDto) throws Exception;
	
	public Ngo findById(Long id);

	public void delete(Long id);
}
