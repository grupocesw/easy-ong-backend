package br.com.grupocesw.easyong.services.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.repositories.UserRepository;
import br.com.grupocesw.easyong.services.JwtTokenService;
import br.com.grupocesw.easyong.services.NgoService;
import br.com.grupocesw.easyong.services.RoleService;
import br.com.grupocesw.easyong.services.UserService;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.services.exceptions.UnauthenticatedUserException;
import br.com.grupocesw.easyong.services.exceptions.UserNotConfirmedException;
import br.com.grupocesw.easyong.services.exceptions.UserNotExistException;
import br.com.grupocesw.easyong.services.exceptions.UsernameAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository repository;
	private final NgoService ngoService;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenService jwtTokenService;

	@Override
	public User create(User user) {
		try {
			boolean userExists = repository.existsByUsername(user.getUsername());
			
			if (userExists) {
				log.warn("username {} already exists.", user.getUsername());

				throw new UsernameAlreadyExistsException(
					String.format("Username %s already exists", user.getUsername()));
			}

			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoles(roleService.getDefaultRoles());
			
			return repository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public User retrieve(Long id) {
		Optional<User> optional = repository.findById(id);

		return optional.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Override
	public User update(Long id, User userRequest) {
		try {
			User user = findById(id);
			
			user.getPerson().setName(userRequest.getPerson().getName());
			user.getPerson().setBirthday(userRequest.getPerson().getBirthday());
			user.getPerson().setGender(userRequest.getPerson().getGender());
			
			user.getCauses().clear();
			
			user.getCauses().addAll(userRequest.getCauses());

			return repository.save(user);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	@Override
	public User updateMe(User userRequest) {
		try {
			User user = getMe();
			
			user.getPerson().setName(userRequest.getPerson().getName());
			user.getPerson().setBirthday(userRequest.getPerson().getBirthday());
			user.getPerson().setGender(userRequest.getPerson().getGender());
			
			user.getCauses().clear();
			
			user.getCauses().addAll(userRequest.getCauses());

			return repository.save(user);
		} catch (EntityNotFoundException e) {
			throw new UserNotExistException();
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

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<User> findCheckedAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public User getMe() {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))
			throw new UnauthenticatedUserException();
		
		User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = findByUsername(authenticatedUser.getUsername()).get();
		
		if (!user.isEnabled()) 
			throw new UnauthenticatedUserException();
		
		return findByUsername(authenticatedUser.getUsername()).get();
	}

	@Override
	public User findById(Long id) {
		return repository.findById(id)
    		.map(user -> user)
    		.orElseThrow(() -> new UserNotExistException());
	}

	@Override
	public String login(String username, String password) {
		
		Optional<User> user = Optional.ofNullable(
			repository.findByUsername(username).orElseThrow(
				() -> new UserNotExistException()
		));

		if (!user.get().isEnabled()) {
			throw new UserNotConfirmedException();
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		return jwtTokenService.generateToken(authentication);
	}
	
	@Override
	public User changePassword(User userRequest) {
		try {
			User user = getMe();
			
			user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

			return repository.save(user);
		} catch (EntityNotFoundException e) {
			throw new UserNotExistException();
		}
	}

	@Override
	public Optional<User> findByUsername(String username) {
		log.info("retrieving user {}", username);
		
		return repository.findByUsername(username);
	}

	@Override
	public void favorite(Long ngoId) {
		User userAuth = this.getMe();
		Ngo ngo = ngoService.retrieve(ngoId);

		if (userAuth.getFavoriteNgos().contains(ngo)) {
			userAuth.getFavoriteNgos().remove(ngo);
		} else {
			userAuth.getFavoriteNgos().add(ngo);
		}

		repository.save(userAuth);
	}
	
	@Override
	public void enableUser(User user) {
		user.setEnabled(true);
		repository.save(user);
	}
	
	@Override
    public UserDetails loadUserByUsername(String username) {
        return findByUsername(username)
    		.map(user -> user)
    		.orElse(new User());
    }

	@Override
	public Boolean existsByUsername(String username) {
		return repository.existsByUsername(username);
	}
	
}
