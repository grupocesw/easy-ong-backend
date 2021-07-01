package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.exceptions.BadRequestException;
import br.com.grupocesw.easyong.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.repositories.SocialCauseRepository;
import br.com.grupocesw.easyong.services.SocialCauseService;
import br.com.grupocesw.easyong.exceptions.DatabaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class SocialCauseServiceImpl implements SocialCauseService {

	private final SocialCauseRepository repository;

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
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Override
	@CacheEvict(value = "socialCauses", allEntries = true)
	public SocialCause update(Long id, SocialCause request) {
		SocialCause cause = retrieve(id);
		cause.setName(request.getName());

		return repository.save(cause);
	}

	@Override
	@CacheEvict(value = "socialCauses", allEntries = true)
	public void delete(Long id) {
		repository.delete(retrieve(id));
	}

	@Override
	public void existsOrThrowsException(Long id) {
		if (!repository.existsById(id))
			throw new BadRequestException("Social Cause not found. Id " + id);
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
	public Set<SocialCause> findByIdIn(Set<Long> ids) {
		return repository.findByIdIn(ids);
	}
}
