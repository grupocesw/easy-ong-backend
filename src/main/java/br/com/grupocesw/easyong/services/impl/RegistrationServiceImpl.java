package br.com.grupocesw.easyong.services.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.grupocesw.easyong.entities.ConfirmationToken;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.services.ConfirmationTokenService;
import br.com.grupocesw.easyong.services.EmailSenderService;
import br.com.grupocesw.easyong.services.RegistrationService;
import br.com.grupocesw.easyong.services.UserService;
import br.com.grupocesw.easyong.services.exceptions.UsernameAlreadyConfirmedException;
import br.com.grupocesw.easyong.services.exceptions.UsernameAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

	private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService mailSenderService;
    
	@Override
	public Boolean register(User request) {
		log.info("Registering user {}", request.getUsername());
		
		boolean userExists = userService.existsByUsername(request.getUsername());
		
		if (userExists) {
			log.warn("Username {} already exists.", request.getUsername());

			throw new UsernameAlreadyExistsException(
				String.format("Username %s already exists", request.getUsername()));
		}

		User userRegisted = userService.create(request);
		String token = getToken();

        ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            User.builder().id(userRegisted.getId()).build()
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        
		try {
			mailSenderService.sendUserRegister(userRegisted, getLink(token));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return true;
	}

    @Override
    @Transactional
    public Boolean confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found."));

        User user = confirmationToken.getUser();
        
        if (user.isEnabled() || confirmationToken.getConfirmedAt() != null) {
        	log.warn("Username {} already confirmed.", user.getUsername());
            throw new UsernameAlreadyConfirmedException();
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired.");
        }

        userService.enableUser(user);
        confirmationTokenService.setConfirmedAt(token);
        
        return true;
    }
    
    private String getLink(String token) {
		return ServletUriComponentsBuilder
			.fromCurrentContextPath()
			.build()
			.toUriString()
			.concat("/api/registration/confirm/" + token);
    }
    
    private String getToken() {
    	return UUID.randomUUID().toString();
    }

	@Override
	public Boolean resendConfirmation(String username) {
		log.info("Resend confirmation user {}", username);

		ConfirmationToken ct = confirmationTokenService
            .findByUsername(username)
            .orElseThrow(() ->
                    new UsernameNotFoundException("Username not found."));
		
		if (ct.getUser().isEnabled()) {
			log.warn("Username {} already confirmed.", ct.getUser().getUsername());
			throw new UsernameAlreadyConfirmedException();
		}

		String token = getToken();
		
		if (ct.getUser().isEnabled() || ct.getConfirmedAt() != null) {
        	log.warn("Username {} already confirmed.", username);
            throw new UsernameAlreadyConfirmedException();
        }

        LocalDateTime expiredAt = ct.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired.");
        }
        
        User user = ct.getUser();

        ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            User.builder().id(user.getId()).build()
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        
		try {
			mailSenderService.sendUserRegister(user, getLink(token));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return true;
	}
}
