package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.Faq;
import br.com.grupocesw.easyong.exceptions.BadRequestException;
import br.com.grupocesw.easyong.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.repositories.FaqRepository;
import br.com.grupocesw.easyong.services.FaqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FaqServiceImpl implements FaqService {

	private final FaqRepository repository;

	@Override
	public List<Faq> findAll() {
		return repository.findAll();
	}

	@Override
	@CacheEvict(value = "faqs", allEntries = true)
	public Faq create(Faq request) {
		log.info("Create faq with question {}", request.getQuestion());

		return repository.save(request);
	}

	@Override
	@Cacheable(value = "faqs", key = "#id")
	public Faq retrieve(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new BadRequestException("FAQ", id));
	}

	@Override
	@CacheEvict(value = "faqs", allEntries = true)
	public Faq update(Long id, Faq request) {
		log.info("Update faq with question {}", request.getQuestion());

		Faq faq = retrieve(id);
		faq.setQuestion(request.getQuestion());
		faq.setAnswer(request.getAnswer());

		return repository.save(faq);
	}

	@Override
	@CacheEvict(value = "faqs", allEntries = true)
	public void delete(Long id) {
		log.info("Delete faq with id {}", id);

		repository.delete(retrieve(id));
	}

	@Override
	public void existsOrThrowsException(Long id) {
		if (!repository.existsById(id))
			throw new BadRequestException("Faq not found. Id " + id);
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
