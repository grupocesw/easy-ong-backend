package br.com.grupocesw.easyong.services;

import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.repositories.NgoRepository;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;

@Service
public class NgoService {

	@Autowired
	private NgoRepository repository;

	public Page<Ngo> findByActivated(Pageable pageable) {
		return repository.findByActivated(pageable);
	}
	
	public Page<Ngo> findSuggested(@PageableDefault(page = 0, size = 1) Pageable pageable, Set<SocialCause> causes) {
		return repository.findSuggested(pageable, causes);
	}

	public Ngo findById(Long id) {
		Optional<Ngo> optional = repository.findById(id);

		return optional.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Ngo insert(Ngo ngo) {		
		try {
			return repository.save(ngo);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Ngo update(Long id, Ngo ngo) {
		try {
			Ngo entity = repository.getOne(id);
			this.updateData(entity, ngo);

			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}		
	}

	private void updateData(Ngo entity, Ngo ngo) {
		entity.setName(ngo.getName());
		entity.setCnpj(ngo.getCnpj());
		entity.setDescription(ngo.getDescription());
		entity.getCauses().addAll(ngo.getCauses());
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
