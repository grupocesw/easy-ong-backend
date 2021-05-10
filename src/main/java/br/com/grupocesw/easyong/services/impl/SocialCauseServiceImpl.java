package br.com.grupocesw.easyong.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.repositories.SocialCauseRepository;
import br.com.grupocesw.easyong.request.dtos.SocialCauseRequestDto;
import br.com.grupocesw.easyong.response.dtos.SocialCauseResponseDto;
import br.com.grupocesw.easyong.services.SocialCauseService;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SocialCauseServiceImpl implements SocialCauseService {

	private SocialCauseRepository repository;
	
	@Override
	public SocialCauseResponseDto create(SocialCauseRequestDto request) {		
		try {
			SocialCause cause = SocialCause.builder()
				.name(request.getName())
				.build();
			
			return new SocialCauseResponseDto(repository.save(cause));
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	@Override
	public SocialCauseResponseDto retrieve(Long id) {
		Optional<SocialCause> optional = repository.findById(id);
		optional.orElseThrow(() -> new ResourceNotFoundException(id));

		return new SocialCauseResponseDto(optional.get());
	}

	@Override
	public SocialCauseResponseDto update(Long id, SocialCauseRequestDto request) {
		try {
			Optional<SocialCause> optional = repository.findById(id);
			optional.orElseThrow(() -> new ResourceNotFoundException(id));
			
			SocialCause cause = optional.get();			
			cause.setName(request.getName());

			return new SocialCauseResponseDto(repository.save(cause));
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
	public List<SocialCauseResponseDto> findAll() {		
		List<SocialCauseResponseDto> result = repository.findAll()
				.stream()
				.map(obj -> new SocialCauseResponseDto(obj))
				.collect(Collectors.toList());
		
		return result;
	}

	@Override
	public Page<SocialCauseResponseDto> findAll(Pageable pageable) {		
		Page<SocialCause> result = repository.findAll(pageable);
		return result.map(obj -> new SocialCauseResponseDto(obj));
	}
	
	@Override
	public Page<SocialCauseResponseDto> findByName(String name, Pageable pageable) {
		Page<SocialCause> result = repository.findByNameContaining(name, pageable);
		return result.map(obj -> new SocialCauseResponseDto(obj));		
	}

	@Override
	public Optional<SocialCause> findById(Long Id) {
		return repository.findById(Id);
	}

	@Override
	public Set<SocialCause> findByIds(Set<Long> ids) {
		return repository.findByIdIn(ids);
	}

}
