package br.com.grupocesw.easyong.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

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
import br.com.grupocesw.easyong.entities.Person;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.payloads.UserRequest;
import br.com.grupocesw.easyong.repositories.UserRepository;
import br.com.grupocesw.easyong.services.JwtTokenService;
import br.com.grupocesw.easyong.services.NgoService;
import br.com.grupocesw.easyong.services.RoleService;
import br.com.grupocesw.easyong.services.UserService;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.services.exceptions.UserNotCheckedException;
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
	public User create(UserRequest request) {
		try {
			boolean userExists = repository.existsByUsername(request.getUsername());
			
			if (userExists) {
				log.warn("username {} already exists.", request.getUsername());

				throw new UsernameAlreadyExistsException(
					String.format("Username %s already exists", request.getUsername()));
			}

			Person person = Person.builder()
					.name(request.getName())
					.birthday(request.getBirthday())
					.gender(request.getGender())
					.build();
			
			User user = User.builder()
					.username(request.getUsername())
					.password(passwordEncoder.encode(request.getPassword()))
					.causes(request.getCauses())
					.person(person)
					.roles(roleService.getDefaultRoles())
					.build();

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
	public User update(Long id, UserRequest userRequest) {
		try {
			User entity = repository.findCheckedById(id);
			updateData(entity, userRequest);

			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(User entity, UserRequest userRequest) {
		entity.getPerson().setName(userRequest.getName());
		entity.getPerson().setBirthday(userRequest.getBirthday());
		entity.getPerson().setGender(userRequest.getGender());

		entity.getCauses().clear();
		entity.getCauses().addAll(userRequest.getCauses());
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
		return repository.findCheckedAll(pageable);
	}
	
	@Override
	public User getMe() {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))
			return null;
		
		User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return findByUsername(authenticatedUser.getUsername());
	}

	@Override
	public User findCheckedById(Long id) {
		return repository.findCheckedById(id);
	}

	@Override
	public String login(String username, String password) {
		
		Optional<User> user = Optional.ofNullable(
				repository.findByUsernameOptional(username).orElseThrow(
						() -> new UserNotExistException()
		));

		if (user.get().getCheckedAt() == null) {
			throw new UserNotCheckedException();
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		return jwtTokenService.generateToken(authentication);
	}

	@Override
	public User findByUsername(String username) {
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
    public UserDetails loadUserByUsername(String username) {
        return repository.findByUsernameOptional(username)
        		.map(user -> user)
        		.orElse(new User());
    }

	@Transactional
	@Override
	public boolean enable(String username) {
		log.info("retrieving user {}", username);
		
		Optional<User> userOptional = Optional.ofNullable(
				repository.findByUsernameOptional(username).orElseThrow(
						() -> new UserNotExistException()
		));
		
		User user = userOptional.get();
		user.setCheckedAt(LocalDateTime.now());
		
		repository.save(user);

		return true;
	}

	@Override
	public Boolean existsByUsername(String username) {
		return repository.existsByUsername(username);
	}

	@Override
	public Optional<User> findByUsernameOptional(String username) {
		return repository.findByUsernameOptional(username);
	}	
}
