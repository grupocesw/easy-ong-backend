package br.com.grupocesw.easyong.services.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.FrequentlyAskedQuestion;
import br.com.grupocesw.easyong.repositories.FrequentlyAskedQuestionRepository;
import br.com.grupocesw.easyong.services.FrequentlyAskedQuestionService;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FrequentlyAskedQuestionServiceImpl implements FrequentlyAskedQuestionService {

	private FrequentlyAskedQuestionRepository repository;

	@Override
	public List<FrequentlyAskedQuestion> findAll() {
		return repository.findAll();
	}
	
	@Override
	public FrequentlyAskedQuestion create(FrequentlyAskedQuestion faq) {		
		try {
			return repository.save(faq);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	@Override
	public FrequentlyAskedQuestion retrieve(Long id) {
		Optional<FrequentlyAskedQuestion> optional = repository.findById(id);

		return optional.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Override
	public FrequentlyAskedQuestion update(Long id, FrequentlyAskedQuestion faq) {
		try {
			FrequentlyAskedQuestion entity = repository.getOne(id);
			this.updateData(entity, faq);

			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}		
	}

	private void updateData(FrequentlyAskedQuestion entity, FrequentlyAskedQuestion faq) {
		entity.setQuestion(faq.getQuestion());
		entity.setAnswer(faq.getAnswer());
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
	public Page<FrequentlyAskedQuestion> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public Page<FrequentlyAskedQuestion> findByQuestionAndAnswer(String filter, Pageable pageable) {
		return repository.findByQuestionAndAnswer(filter, pageable);
	}

}
