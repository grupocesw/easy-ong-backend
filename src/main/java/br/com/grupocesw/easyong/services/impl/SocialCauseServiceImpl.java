package br.com.grupocesw.easyong.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.repositories.SocialCauseRepository;
import br.com.grupocesw.easyong.services.SocialCauseService;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class SocialCauseServiceImpl implements SocialCauseService {

	private SocialCauseRepository repository;

	@Override
	@CacheEvict(value = "socialCauses", allEntries = true)
	public SocialCause create(SocialCause socialCause) {
		try {
			return repository.save(socialCause);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	@Cacheable(value = "socialCauses", key = "#id")
	public SocialCause retrieve(Long id) {
		Optional<SocialCause> optional = repository.findById(id);
		optional.orElseThrow(() -> new ResourceNotFoundException(id));

		return optional.get();
	}

	@Override
	@CacheEvict(value = "socialCauses", allEntries = true)
	public SocialCause update(Long id, SocialCause request) {
		try {
			Optional<SocialCause> optional = repository.findById(id);
			optional.orElseThrow(() -> new ResourceNotFoundException(id));

			SocialCause cause = optional.get();
			cause.setName(request.getName());

			return repository.save(cause);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	@Override
	@CacheEvict(value = "socialCauses", allEntries = true)
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
	public List<SocialCause> findAll() {
		return repository.findAll();
	}

	@Override
	@Cacheable(value = "socialCauses", key = "#pageable.pageSize")
	public Page<SocialCause> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Page<SocialCause> findByName(String name, Pageable pageable) {
		return repository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public Optional<SocialCause> findById(Long Id) {
		return repository.findById(Id);
	}

	@Override
	public Set<SocialCause> findByIdIn(Set<Long> ids) {
		return repository.findByIdIn(ids);
	}
}
