package br.com.grupocesw.easyong.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.repositories.UserRepository;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.utilities.UserUtility;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public Page<User> findByChecked(Pageable pageable) {
		return repository.findByChecked(pageable);
	}

	public User findById(Long id) {
		Optional<User> optional = repository.findById(id);

		return optional.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public User insert(User user) {		
		try {
			user.setPassword(UserUtility.encryptPassword(user.getPassword()));
			return repository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public User update(Long id, User user) {
		try {
			User entity = repository.getOne(id);
			this.updateData(entity, user);

			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}		
	}

	private void updateData(User entity, User user) {
		entity.setName(user.getName());
		entity.setBirthday(user.getBirthday());
		entity.setGender(user.getGender());
		entity.getCauses().addAll(user.getCauses());
		entity.getFavoriteNgos().addAll(user.getFavoriteNgos());
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
