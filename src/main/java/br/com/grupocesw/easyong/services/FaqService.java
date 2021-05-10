package br.com.grupocesw.easyong.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.request.dtos.FaqRequestDto;
import br.com.grupocesw.easyong.response.dtos.FaqResponseDto;

@Service
public interface FaqService {

	public FaqResponseDto create(FaqRequestDto request);

	public FaqResponseDto retrieve(Long id);

	public FaqResponseDto update(Long id, FaqRequestDto request);

	public void delete(Long id);
	
	public List<FaqResponseDto> findAll();

	public Page<FaqResponseDto> findAll(Pageable pageable);

	public Page<FaqResponseDto> findByQuestionAndAnswer(String filter, Pageable pageable);
}
