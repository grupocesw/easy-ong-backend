package br.com.grupocesw.easyong.services.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Faq;
import br.com.grupocesw.easyong.repositories.FaqRepository;
import br.com.grupocesw.easyong.services.FaqService;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FaqServiceImpl implements FaqService {

	private final FaqRepository repository;

	@Override
	public List<Faq> findAll() {
		return repository.findAll();
	}

	@Override
	@CacheEvict(value = "faqs", allEntries = true)
	public Faq create(Faq faq) {
		try {
			return repository.save(faq);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	@Cacheable(value = "faqs", key = "#id")
	public Faq retrieve(Long id) {
		Optional<Faq> optional = repository.findById(id);
		optional.orElseThrow(() -> new ResourceNotFoundException(id));

		return optional.get();
	}

	@Override
	@CacheEvict(value = "faqs", allEntries = true)
	public Faq update(Long id, Faq request) {
		try {
			Optional<Faq> optional = repository.findById(id);
			optional.orElseThrow(() -> new ResourceNotFoundException(id));

			Faq faq = optional.get();

			faq.setQuestion(request.getQuestion());
			faq.setAnswer(request.getAnswer());

			return repository.save(faq);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	@Override
	@CacheEvict(value = "faqs", allEntries = true)
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
	@Cacheable(value = "faqs", key = "#pageable.pageSize")
	public Page<Faq> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Page<Faq> findByQuestionAndAnswer(String filter, Pageable pageable) {
		return repository.findByQuestionAndAnswer(filter, pageable);
	}

}
