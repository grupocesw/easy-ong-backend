package br.com.grupocesw.easyong.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.request.dtos.FaqRequestDto;
import br.com.grupocesw.easyong.response.dtos.FaqResponseDto;

@Service
public interface FaqService {

	FaqResponseDto create(FaqRequestDto request);

	FaqResponseDto retrieve(Long id);

	FaqResponseDto update(Long id, FaqRequestDto request);

	void delete(Long id);
	
	List<FaqResponseDto> findAll();

	Page<FaqResponseDto> findAll(Pageable pageable);

	Page<FaqResponseDto> findByQuestionAndAnswer(String filter, Pageable pageable);
}
