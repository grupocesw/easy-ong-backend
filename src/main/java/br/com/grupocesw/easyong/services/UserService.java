package br.com.grupocesw.easyong.services;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.repositories.UserRepository;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.utilities.UserUtility;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private NgoService ngoService;

	public Page<User> findByChecked(Pageable pageable) {
		return repository.findByChecked(pageable);
	}

	public User getOneChecked(Long id) {
		Optional<User> optional = repository.getOneChecked(id);

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
			User entity = this.getOneChecked(id);
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
		
		entity.getCauses().clear();	
		entity.getCauses().addAll(user.getCauses());
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
	
	public User check(Long id, Boolean goCheck) {
		try {
			Optional<User> optional = goCheck ? 
					repository.getOneNotChecked(id) :
					repository.getOneChecked(id);
			
			optional.orElseThrow(() -> new ResourceNotFoundException(id));
			User user = optional.get();
			
			if (goCheck) {
				user.setCheckedAt(LocalDateTime.now());
			} else {
				user.setCheckedAt(null);
			}
			
			return repository.save(user);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}		
	}
	
	public User favoriteNgo(Long userId, Long ngoId, Boolean goFavorite) {
		try {
			Optional<User> optional = repository.getOneChecked(userId);
			optional.orElseThrow(() -> new ResourceNotFoundException(userId));

			User user = optional.get();
			Ngo ngo = ngoService.findById(ngoId);
			
			if (goFavorite) {
				user.getFavoriteNgos().add(ngo);
			} else {
				user.getFavoriteNgos().remove(ngo);
			}

			return repository.save(user);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(userId);
		}		
	}

}
