package br.com.grupocesw.easyong.services.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import br.com.grupocesw.easyong.entities.Picture;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.request.dtos.LoginRequestDto;
import br.com.grupocesw.easyong.response.dtos.JwtAuthenticationResponseDto;
import br.com.grupocesw.easyong.services.*;
import io.jsonwebtoken.Claims;
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
import br.com.grupocesw.easyong.request.dtos.UserPasswordRequestDto;
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
	public User create(User request) {
		try {
			boolean userExists = repository.existsByUsernameIgnoreCase(request.getUsername());
			
			if (userExists) {
				log.warn("username {} already exists.", request.getUsername());

				throw new UsernameAlreadyExistsException(
					String.format("Username %s already exists", request.getUsername()));
			}

			if (request.getCauses() != null && request.getCauses().size() > 0) {
				Set<SocialCause> causes = socialCauseService.findByIdIn(
						request.getCauses().stream().map(c -> c.getId())
								.collect(Collectors.toSet())
				);

				if (causes.isEmpty())
					throw new IllegalArgumentException("Social causes not found");

				request.setCauses(causes);
			}

			User user = User.builder()
				.username(request.getUsername().trim().toLowerCase())
				.password(passwordEncoder.encode(request.getPassword().trim()))
				.person(request.getPerson())
				.enabled(true) // TODO remove after implements service e-mail in prod
				.roles(roleService.getDefaultRoles())
				.causes(request.getCauses())
				.build();

			User userSaved = repository.save(user);

			if (userSaved.getPicture() == null) {
				userSaved.setPicture(Picture.builder().build());
			}

			return userSaved;
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public User retrieve(Long id) {
		Optional<User> userOptional = repository.findById(id);
		userOptional.orElseThrow(() -> new ResourceNotFoundException(id));

		if (userOptional.get().getPicture() == null) {
			userOptional.get().setPicture(Picture.builder().build());
		}

		return userOptional.get();
	}

	@Override
	public User update(Long id, User request) {
		try {
			User user = findById(id);
			user.getPerson().setName(request.getPerson().getName());
			user.getPerson().setBirthday(request.getPerson().getBirthday());
			user.getPerson().setGender(request.getPerson().getGender());

			if (request.getCauses() != null && request.getCauses().size() > 0) {
				Set<SocialCause> causes = socialCauseService.findByIdIn(
						request.getCauses().stream().map(c -> c.getId())
								.collect(Collectors.toSet())
				);

				if (causes.isEmpty())
					throw new IllegalArgumentException("Social causes not found");

				user.getCauses().clear();
				user.getCauses().addAll(causes);
			}

			return repository.save(user);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	@Override
	public User updateMe(User request) {
		try {
			User user = getAuthUser();

			user.getPerson().setName(request.getPerson().getName());
			user.getPerson().setBirthday(request.getPerson().getBirthday());
			user.getPerson().setGender(request.getPerson().getGender());

			if (request.getCauses() != null && request.getCauses().size() > 0) {
				Set<SocialCause> causes = socialCauseService.findByIdIn(
						request.getCauses().stream().map(c -> c.getId())
								.collect(Collectors.toSet())
				);

				if (causes.isEmpty())
					throw new IllegalArgumentException("Social causes not found");

				user.getCauses().clear();
				user.getCauses().addAll(causes);
			}

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
		return repository.findAll().stream()
				.map(user -> {
					if (user.getPicture() == null) {
						user.setPicture(Picture.builder().build());
					}

					return user;
				})
				.collect(Collectors.toList());
	}

	@Override
	public Page<User> findCheckedAll(Pageable pageable) {
		return repository.findAll(pageable)
				.map(user -> {
					if (user.getPicture() == null) {
						user.setPicture(Picture.builder().build());
					}

					return user;
				});
	}
	
	@Override
	public User getMe() {
		return getAuthUser();
	}

	public User getAuthUser() {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))
			throw new UnauthenticatedUserException();
		
		User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = findByUsername(authenticatedUser.getUsername()).get();

		if (!user.isEnabled()) 
			throw new UnauthenticatedUserException();

		User userFound = findByUsername(authenticatedUser.getUsername()).get();

		if (userFound.getPicture() == null) {
			userFound.setPicture(Picture.builder().build());
		}
		
		return userFound;
	}
	
	public User findById(Long id) {
		return repository.findById(id)
    		.map(user -> user)
    		.orElseThrow(() -> new UserNotExistException());
	}

	@Override
	public JwtAuthenticationResponseDto login(LoginRequestDto requestDto) {
		String username = requestDto.getUsername().trim().toLowerCase();
		String password = requestDto.getPassword().trim();

		Optional<User> user = Optional.ofNullable(
			repository.findByUsernameIgnoreCase(username).orElseThrow(
				() -> new UserNotExistException()
		));

		if (!user.get().isEnabled()) {
			throw new UserNotConfirmedException();
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		String token = jwtTokenService.generateToken(authentication);
		Claims claims = jwtTokenService.getClaimsFromJWT(token);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

		return new JwtAuthenticationResponseDto()
				.builder()
				.username(claims.getSubject())
				.accessToken(token)
				.expiration(df.format(claims.getExpiration()))
				.tokenType("Bearer")
				.build();
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

		return Optional.ofNullable(
				repository.findByUsernameIgnoreCase(username).orElseThrow(
						() -> new UserNotExistException()
				));
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
        return findByUsername(username).orElse(new User());
    }

	@Override
	public Boolean existsByUsername(String username) {
		return repository.existsByUsernameIgnoreCase(username);
	}

	@Override
	public Page<Ngo> getFavoriteNgos(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        
		List<Ngo> ngos = getAuthUser().getFavoriteNgos()
				.stream()
				.collect(Collectors.toList());

        if (ngos.size() < startItem) {
        	ngos = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, ngos.size());
            ngos = ngos.subList(startItem, toIndex);
        }
		
		Page<Ngo> page = new PageImpl<>(
				ngos, 
				PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), 
				ngos.size()
			);
		
		return page;
	}
	
}
