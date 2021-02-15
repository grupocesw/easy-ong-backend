package br.com.grupocesw.easyong.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.repositories.NgoRepository;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;

@Service
public class NgoService {

	@Autowired
	private NgoRepository repository;

	public List<Ngo> findAll() {
		return repository.findAll();
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
			Ngo ngoEntity = repository.getOne(id);
			this.updateData(ngoEntity, ngo);

			return repository.save(ngoEntity);
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
