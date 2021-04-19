package br.com.grupocesw.easyong.services.impl;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.repositories.NgoRepository;
import br.com.grupocesw.easyong.services.NgoService;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;

@Service
public class NgoServiceImpl implements NgoService {

	@Autowired
	private NgoRepository repository;

	@Override
	public Page<Ngo> findByActivated(Pageable pageable) {
		return repository.findByActivated(pageable);
	}

	@Override
	public Page<Ngo> findSuggested(Pageable pageable) {
		return repository.findSuggested(pageable);
	}

	@Override
	public Ngo create(Ngo ngo) {
		try {
			return repository.save(ngo);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public Ngo retrieve(Long id) {
		Optional<Ngo> optional = repository.findById(id);

		return optional.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Override
	public Ngo update(Long id, Ngo payload) throws Exception {
		try {
			Ngo ngo = repository.getOne(id);
			this.updateData(ngo, payload);

			return repository.save(ngo);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	private void updateData(Ngo ngo, Ngo payload) {
		ngo.setName(payload.getName());
		ngo.setCnpj(payload.getCnpj());
		ngo.setDescription(payload.getDescription());
		ngo.setActivated(payload.getActivated());

		if (payload.getAddress() != null) {
			ngo.getAddress().setNumber(payload.getAddress().getNumber());
			ngo.getAddress().setComplement(payload.getAddress().getComplement());
			ngo.getAddress().setLatitude(payload.getAddress().getLatitude());
			ngo.getAddress().setLongitude(payload.getAddress().getLongitude());

			if (payload.getAddress().getStreet() != null) {
				ngo.getAddress().getStreet().setId(payload.getAddress().getStreet().getId());
			}
		}

		if (payload.getCauses() != null) {
			ngo.getCauses().clear();
			ngo.getCauses().addAll(payload.getCauses());
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

}
