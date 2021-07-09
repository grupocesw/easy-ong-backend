package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.ConfirmationToken;
import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.exceptions.BadRequestException;
import br.com.grupocesw.easyong.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.exceptions.UserNotExistException;
import br.com.grupocesw.easyong.exceptions.UsernameAlreadyExistsException;
import br.com.grupocesw.easyong.repositories.UserRepository;
import br.com.grupocesw.easyong.response.dtos.JwtAuthenticationResponseDto;
import br.com.grupocesw.easyong.security.TokenProvider;
import br.com.grupocesw.easyong.services.*;
import br.com.grupocesw.easyong.utils.AppUtil;
import br.com.grupocesw.easyong.utils.PasswordUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository repository;
	private final NgoService ngoService;
	private final SocialCauseService socialCauseService;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
	private final ConfirmationTokenService confirmationTokenService;
	private final EmailSenderService mailSenderService;

	@Override
	public User create(User request) {
		log.info("Create user with username {}", request.getUsername());

		boolean userExists = repository.existsByUsernameIgnoreCase(request.getUsername());

		if (userExists) {
			log.warn("Username {} already exists", request.getUsername());

			throw new UsernameAlreadyExistsException(
				String.format("Username %s already exists", request.getUsername()));
		}

		if (request.getCauses() != null && request.getCauses().size() > 0) {
			Set<SocialCause> causes = socialCauseService.findByIdIn(
					request.getCauses().stream().map(c -> c.getId())
							.collect(Collectors.toSet())
			);

			if (causes.isEmpty()) {
				log.warn("Social cause not found {} already exists", request.getCauses());
				throw new IllegalArgumentException("Social causes not found");
			}

			request.setCauses(causes);
		}

		User user = User.builder()
			.username(request.getUsername())
			.password(passwordEncoder.encode(
				PasswordUtil.isPasswordOk(request.getPassword())
			))
			.person(request.getPerson())
			.roles(roleService.getDefaultRoles())
			.causes(request.getCauses())
			.build();

		if(request.getPicture() != null)
			user.setPicture(request.getPicture());

		if(request.getProvider() != null) {
			user.setProvider(request.getProvider());
			user.setProviderId(request.getProviderId());
		}

		return repository.save(user);
	}

	@Override
	public User retrieve(Long id) {
		User user = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));

		return user;
	}

	@Override
	public User update(Long id, User request) {
		log.info("Update to username {}", request.getUsername());

		User user = retrieve(id);
		user.getPerson().setName(request.getPerson().getName());
		user.getPerson().setBirthday(request.getPerson().getBirthday());
		user.getPerson().setGender(request.getPerson().getGender());

		if (request.getPicture() != null)
			user.getPicture().setUrl(request.getPicture().getUrl());

		if (request.getCauses() != null && request.getCauses().size() > 0) {
			Set<SocialCause> causes = socialCauseService.findByIdIn(
				request.getCauses()
					.stream()
					.map(c -> c.getId())
					.collect(Collectors.toSet())
			);

			if (causes.isEmpty())
				throw new IllegalArgumentException("Social causes not found");

			user.getCauses().clear();
			user.getCauses().addAll(causes);
		}

		return repository.save(user);
	}

	@Override
	public void delete(Long id) {
		log.info("Delete to id {}", id);

		repository.delete(retrieve(id));
	}

	@Override
	public void existsOrThrowsException(Long id) {
		if (!repository.existsById(id))
			throw new BadRequestException("User not found. Id " + id);
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
	public JwtAuthenticationResponseDto login(User request) {
		log.info("Login to username {}", request.getUsername());

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		String token = tokenProvider.createToken(authentication);
		Claims claims = tokenProvider.getClaimsFromJWT(token);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

		return new JwtAuthenticationResponseDto()
				.builder()
				.subject(claims.getSubject())
				.accessToken(token)
				.expiration(df.format(claims.getExpiration()))
				.build();
	}

	@Override
	public void changePassword(Long id, User request) {
		User user = retrieve(id);
		user.setPassword(passwordEncoder.encode(
			PasswordUtil.isPasswordOk(request.getPassword())
		));

		log.info("Change password to username {}", user.getUsername());

		repository.save(user);
	}

	@Override
	public void recoverPassword(User request) {
		log.info("Change password to username {}", request.getUsername());

		User user = findByUsernameOrThrowUserNotExistException(request.getUsername());
		Optional<ConfirmationToken> tokenFound = confirmationTokenService
				.findByUsernameNotExpiratedAndNotConfirmed(user.getUsername());

		if (tokenFound.isPresent()) {
			log.warn("Exists token active this request to username {}", request.getUsername());
			var minutes = Long.toString(Duration.between(LocalDateTime.now(), tokenFound.get().getExpiresAt()).plusMinutes(1L).toMinutes());
			throw new BadRequestException("Email already sent. Please wait " + minutes + " minutes for new request");
		}

		String token = confirmationTokenService.generateToken();

		confirmationTokenService.save(
			ConfirmationToken.builder()
				.token(token)
				.user(user)
				.build()
		);

		try {
			mailSenderService.sendRecoverPassword(user, AppUtil.getRootUrlAppConcatPath("/recover-password/" + token));
			log.info("Send password recovery email to username {}", user.getUsername());
		} catch (Exception e) {
			log.error("Error sending password recovery email to username {}", user.getUsername());
			System.err.println(e.getMessage());
		}

	}

	@Override
	@Transactional
	public User confirmUserRecoverPassword(String token, User request) {
		ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);
		User user = confirmationToken.getUser();

		user.setPassword(passwordEncoder.encode(
			PasswordUtil.isPasswordOk(request.getPassword())
		));

		confirmationTokenService.setConfirmedAt(token);

		log.info("Recover password to username {}", user.getUsername());

		return user;
	}

	@Override
	public User findByUsernameOrThrowUserNotExistException(String username) {
		log.info("Find username {} or throw UserNotExistException {}", username);

		return repository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new UserNotExistException());
	}

	@Override
	public Optional<User> findByUsername(String username) {
		log.info("Find username {}", username);

		return repository.findByUsernameIgnoreCase(username);
	}

	@Override
	public void favorite(Long userId, Long ngoId) {
		log.info("User id {} favorite Ngo id {}", userId, ngoId);

		User userAuth = retrieve(userId);
		Ngo ngo = ngoService.retrieve(ngoId);

		if (userAuth.getFavoriteNgos().contains(ngo)) {
			userAuth.getFavoriteNgos().remove(ngo);
		} else {
			userAuth.getFavoriteNgos().add(ngo);
		}

		repository.save(userAuth);
	}

	@Override
	public Page<Ngo> getFavoriteNgos(Long userId, Pageable pageable) {
		log.info("List favorite Ngos id");

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

		List<Ngo> ngos = retrieve(userId).getFavoriteNgos().stream().collect(Collectors.toList());

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

	@Override
	public void enableUser(User user) {
		user.setEnabled(true);
		repository.save(user);

		log.info("Enabled username {}", user.getUsername());
	}

	@Override
	public Boolean existsByUsername(String username) {
		return repository.existsByUsernameIgnoreCase(username);
	}

}
