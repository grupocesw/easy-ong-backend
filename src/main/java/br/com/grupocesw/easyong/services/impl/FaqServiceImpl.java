package br.com.grupocesw.easyong.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Faq;
import br.com.grupocesw.easyong.repositories.FaqRepository;
import br.com.grupocesw.easyong.request.dtos.FaqCreateRequestDto;
import br.com.grupocesw.easyong.request.dtos.FaqUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.FaqResponseDto;
import br.com.grupocesw.easyong.services.FaqService;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FaqServiceImpl implements FaqService {

	private FaqRepository repository;

	@Override
	public List<FaqResponseDto> findAll() {		
		List<FaqResponseDto> result = repository.findAll()
				.stream()
				.map(obj -> new FaqResponseDto(obj))
				.collect(Collectors.toList());
		
		return result;
	}
	
	@Override
	public FaqResponseDto create(FaqCreateRequestDto faqDto) {		
		try {
			return new FaqResponseDto(repository.save(faqDto.build()));
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	@Override
	public FaqResponseDto retrieve(Long id) {
		Optional<Faq> optional = repository.findById(id);
		optional.orElseThrow(() -> new ResourceNotFoundException(id));

		return new FaqResponseDto(optional.get());
	}

	@Override
	public FaqResponseDto update(Long id, FaqUpdateRequestDto faqDto) {
		try {
			Optional<Faq> optional = repository.findById(id);
			optional.orElseThrow(() -> new ResourceNotFoundException(id));
			
			Faq faq = optional.get();
			
			faq.setQuestion(faqDto.getQuestion());
			faq.setAnswer(faqDto.getAnswer());

			return new FaqResponseDto(repository.save(faq));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}		
	}

	@Override
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public Page<FaqResponseDto> findAll(Pageable pageable) {		
		Page<Faq> result = repository.findAll(pageable);
		return result.map(obj -> new FaqResponseDto(obj));
	}
	
	@Override
	public Page<FaqResponseDto> findByQuestionAndAnswer(String filter, Pageable pageable) {
		Page<Faq> result = repository.findByQuestionAndAnswer(filter, pageable);
		return result.map(obj -> new FaqResponseDto(obj));		
	}

}