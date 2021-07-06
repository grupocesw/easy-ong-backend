package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.ConfirmationToken;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.exceptions.ExpiredTokenRequestException;
import br.com.grupocesw.easyong.exceptions.UsernameAlreadyConfirmedException;
import br.com.grupocesw.easyong.exceptions.UsernameAlreadyExistsException;
import br.com.grupocesw.easyong.services.ConfirmationTokenService;
import br.com.grupocesw.easyong.services.EmailSenderService;
import br.com.grupocesw.easyong.services.RegistrationService;
import br.com.grupocesw.easyong.services.UserService;
import br.com.grupocesw.easyong.utils.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

	private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService mailSenderService;

	@Override
	public User register(User request) {
		log.info("Registering user {}", request.getUsername());
		
		boolean userExists = userService.existsByUsername(request.getUsername());
		
		if (userExists) {
			log.warn("Username {} already exists.", request.getUsername());

			throw new UsernameAlreadyExistsException(
				String.format("Username %s already exists", request.getUsername()));
		}

		User userRegisted = userService.create(request);
		String token = confirmationTokenService.generateToken();

        confirmationTokenService.save(
			ConfirmationToken.builder()
				.token(token)
				.user(userRegisted)
				.build()
		);
        
		try {
			mailSenderService.sendUserRegister(userRegisted,
					AppUtil.getRootUrlAppConcatPath("/confirmation-account/" + token)
			);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return userRegisted;
	}

    @Override
    @Transactional
    public User confirmUserAccount(String token) {
		ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);
		User user = confirmationToken.getUser();

		if (user.isEnabled()) {
			log.warn("Username {} already confirmed.", user.getUsername());
			throw new UsernameAlreadyConfirmedException();
		}

        userService.enableUser(user);
        confirmationTokenService.setConfirmedAt(token);

        return user;
    }

	@Override
	public void resendConfirmation(User request) {
		log.info("Resend confirmation user {}", request.getUsername());

		ConfirmationToken ct = confirmationTokenService
            .findByUsername(request.getUsername())
            .orElseThrow(() ->
                    new UsernameNotFoundException("Username not found."));
		
		if (ct.getUser().isEnabled()) {
			log.warn("Username {} already confirmed.", ct.getUser().getUsername());
			throw new UsernameAlreadyConfirmedException();
		}

		if (ct.getUser().isEnabled() || ct.getConfirmedAt() != null) {
        	log.warn("Username {} already confirmed.", request.getUsername());
            throw new UsernameAlreadyConfirmedException();
        }

        LocalDateTime expiredAt = ct.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
           throw new ExpiredTokenRequestException();
        }
        
        User user = ct.getUser();
		String token = confirmationTokenService.generateToken();

		confirmationTokenService.save(
			ConfirmationToken.builder()
				.token(token)
				.user(user)
				.build()
		);
        
		try {
			mailSenderService.sendUserRegister(user, AppUtil.getRootUrlAppConcatPath("/recover-password/" + token));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
