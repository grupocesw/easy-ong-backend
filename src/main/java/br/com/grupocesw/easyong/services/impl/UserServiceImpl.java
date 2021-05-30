package br.com.grupocesw.easyong.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.services.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import br.com.grupocesw.easyong.request.dtos.UserCreateRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserPasswordRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.NgoResponseDto;
import br.com.grupocesw.easyong.response.dtos.UserResponseDto;
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
	private final SocialCauseService socialCauseService;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenService jwtTokenService;

	@Override
	public UserResponseDto create(UserCreateRequestDto request) {
		try {
			boolean userExists = repository.existsByUsernameIgnoreCase(request.getUsername());
			
			if (userExists) {
				log.warn("username {} already exists.", request.getUsername());

				throw new UsernameAlreadyExistsException(
					String.format("Username %s already exists", request.getUsername()));
			}

			User user = request.build();

			if (request.getCauseIds() != null && request.getCauseIds().size() > 0) {
				Set<SocialCause> causes = socialCauseService.findByIdIn(request.getCauseIds());

				if (causes.isEmpty())
					throw new IllegalArgumentException("Social causes not found");

				user.setCauses(causes);
			}

			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoles(roleService.getDefaultRoles());
			
			// TODO remove after implements service e-mail in prod
			user.setEnabled(true);
			
			return new UserResponseDto(repository.save(user));
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public UserResponseDto retrieve(Long id) {
		Optional<User> optional = repository.findById(id);
		return new UserResponseDto(
				optional.orElseThrow(() -> new ResourceNotFoundException(id)));
	}

	@Override
	public UserResponseDto update(Long id, UserUpdateRequestDto request) {
		try {
			User user = findById(id);
			user.getPerson().setName(request.getName());
			user.getPerson().setBirthday(request.getBirthday());
			user.getPerson().setGender(request.getGender());

			if (request.getCauseIds() != null && request.getCauseIds().size() > 0) {
				user.getCauses().clear();
				user.getCauses().addAll(socialCauseService.findByIdIn(request.getCauseIds()));
			}

			return new UserResponseDto(repository.save(user));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	@Override
	public UserResponseDto updateMe(UserUpdateRequestDto request) {
		try {
			User user = getAuthUser();
			
			user.getPerson().setName(request.getName());
			user.getPerson().setBirthday(request.getBirthday());
			user.getPerson().setGender(request.getGender());

			if (request.getCauseIds() != null && request.getCauseIds().size() > 0) {
				user.getCauses().clear();
				user.getCauses().addAll(socialCauseService.findByIdIn(request.getCauseIds()));
			}

			return new UserResponseDto(repository.save(user));
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
	public List<UserResponseDto> findAll() {
		return repository.findAll().stream()
				.map(user -> new UserResponseDto(user))
				.collect(Collectors.toList());
	}

	@Override
	public Page<UserResponseDto> findCheckedAll(Pageable pageable) {
		return repository.findAll(pageable)
				.map(user -> new UserResponseDto(user));
	}
	
	@Override
	public UserResponseDto getMe() {
		return new UserResponseDto(getAuthUser());
	}

	public User getAuthUser() {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))
			throw new UnauthenticatedUserException();
		
		User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = findByUsername(authenticatedUser.getUsername()).get();
		
		if (!user.isEnabled()) 
			throw new UnauthenticatedUserException();
		
		return findByUsername(authenticatedUser.getUsername()).get(); 
	}
	
	public User findById(Long id) {
		return repository.findById(id)
    		.map(user -> user)
    		.orElseThrow(() -> new UserNotExistException());
	}

	@Override
	public String login(String username, String password) {		
		Optional<User> user = Optional.ofNullable(
			repository.findByUsernameIgnoreCase(username).orElseThrow(
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
	public void changePassword(UserPasswordRequestDto userDto) {
		try {
			User user = getAuthUser();			
			user.setPassword(passwordEncoder.encode(userDto.build().getPassword()));

			repository.save(user);
		} catch (EntityNotFoundException e) {
			throw new UserNotExistException();
		}
	}

	@Override
	public Optional<User> findByUsername(String username) {
		log.info("retrieving user {}", username);
		
		return repository.findByUsernameIgnoreCase(username);
	}

	@Override
	public void favorite(Long ngoId) {
		User userAuth = getAuthUser();
		Ngo ngo = ngoService.findById(ngoId);

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
		return repository.existsByUsernameIgnoreCase(username);
	}

	@Override
	public Page<NgoResponseDto> getFavoriteNgos(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        
		List<NgoResponseDto> ngos = getAuthUser().getFavoriteNgos().stream()
				.map(ngo -> new NgoResponseDto(ngo))
				.collect(Collectors.toList());

        if (ngos.size() < startItem) {
        	ngos = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, ngos.size());
            ngos = ngos.subList(startItem, toIndex);
        }
		
		Page<NgoResponseDto> page = new PageImpl<>(
				ngos, 
				PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), 
				ngos.size()
			);
		
		return page;
	}
	
}
