package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.ConfirmationToken;
import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.exceptions.*;
import br.com.grupocesw.easyong.repositories.UserRepository;
import br.com.grupocesw.easyong.response.dtos.JwtAuthenticationResponseDto;
import br.com.grupocesw.easyong.services.*;
import br.com.grupocesw.easyong.utils.AppUtil;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository repository;
	private final NgoService ngoService;
	private final SocialCauseService socialCauseService;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenService jwtTokenService;
	private final ConfirmationTokenService confirmationTokenService;
	private final EmailSenderService mailSenderService;

	@Override
	public User create(User request) {
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

		return repository.save(
			User.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.person(request.getPerson())
				.enabled(true) // TODO remove after implements service e-mail in prod
				.roles(roleService.getDefaultRoles())
				.causes(request.getCauses())
				.build()
		);
	}

	@Override
	public User retrieve(Long id) {
		User user = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));

		return user;
	}

	@Override
	public User update(Long id, User request) {
		User user = retrieve(id);
		user.getPerson().setName(request.getPerson().getName());
		user.getPerson().setBirthday(request.getPerson().getBirthday());
		user.getPerson().setGender(request.getPerson().getGender());

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
	public User updateMe(User request) {
		return repository.save(
			update(getAuthUser().getId(), request)
		);
	}

	@Override
	public void delete(Long id) {
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
	public User getMe() {
		return getAuthUser();
	}

	public User getAuthUser() {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))
			throw new UnauthenticatedUserException();
		
		User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = findByUsername(authenticatedUser.getUsername()).get();

		if (!user.isEnabled()) 
			throw new DisabledUserException();
		
		return user;
	}

	@Override
	public JwtAuthenticationResponseDto login(User request) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		String token = jwtTokenService.generateToken(authentication);
		Claims claims = jwtTokenService.getClaimsFromJWT(token);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

		return new JwtAuthenticationResponseDto()
				.builder()
				.username(claims.getSubject())
				.accessToken(token)
				.expiration(df.format(claims.getExpiration()))
				.build();
	}
	
	@Override
	public void changePassword(User request) {
		User user = getAuthUser();
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		repository.save(user);
	}

	@Override
	public void recoverPassword(User request) {
		User user = findByUsername(request.getUsername()).get();
		String token = confirmationTokenService.generateToken();

		confirmationTokenService.save(
			ConfirmationToken.builder()
				.token(token)
				.user(user)
				.build()
		);

		try {
			mailSenderService.sendRecoverPassword(user, AppUtil.getRootUrlAppConcatPath("/recover-password/" + token));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}
	@Override
	@Transactional
	public User confirmUserRecoverPassword(String token, User request) {
		ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);
		User user = confirmationToken.getUser();
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		confirmationTokenService.setConfirmedAt(token);

		return user;
	}


	@Override
	public Optional<User> findByUsername(String username) {
		log.info("findByUsername user {}", username);

		return Optional.ofNullable(
			repository.findByUsernameIgnoreCase(username).orElseThrow(
					() -> new UserNotExistException()
			));
	}

	@Override
	public void favorite(Long ngoId) {
		User userAuth = getAuthUser();
		Ngo ngo = ngoService.retrieve(ngoId);

		if (userAuth.getFavoriteNgos().contains(ngo)) {
			userAuth.getFavoriteNgos().remove(ngo);
		} else {
			userAuth.getFavoriteNgos().add(ngo);
		}

		repository.save(userAuth);
	}

	@Override
	public Page<Ngo> getFavoriteNgos(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        
		List<Ngo> ngos = getAuthUser().getFavoriteNgos().stream().collect(Collectors.toList());

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
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		return findByUsername(username).orElse(new User());
	}

	@Override
	public Boolean existsByUsername(String username) {
		return repository.existsByUsernameIgnoreCase(username);
	}
	
}
