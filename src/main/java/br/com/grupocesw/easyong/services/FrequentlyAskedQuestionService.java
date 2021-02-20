package br.com.grupocesw.easyong.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.FrequentlyAskedQuestion;
import br.com.grupocesw.easyong.repositories.FrequentlyAskedQuestionRepository;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;

@Service
public class FrequentlyAskedQuestionService {

	@Autowired
	private FrequentlyAskedQuestionRepository repository;

	public Page<FrequentlyAskedQuestion> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public FrequentlyAskedQuestion findById(Long id) {
		Optional<FrequentlyAskedQuestion> optional = repository.findById(id);

		return optional.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public FrequentlyAskedQuestion insert(FrequentlyAskedQuestion faq) {		
		try {
			return repository.save(faq);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

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

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

}
