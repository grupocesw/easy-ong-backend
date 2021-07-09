package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.ConfirmationToken;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.exceptions.BadRequestException;
import br.com.grupocesw.easyong.exceptions.UsernameAlreadyConfirmedException;
import br.com.grupocesw.easyong.exceptions.UsernameAlreadyExistsException;
import br.com.grupocesw.easyong.services.ConfirmationTokenService;
import br.com.grupocesw.easyong.services.EmailSenderService;
import br.com.grupocesw.easyong.services.RegistrationService;
import br.com.grupocesw.easyong.services.UserService;
import br.com.grupocesw.easyong.utils.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

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

		User user = userService.create(request);
		String token = confirmationTokenService.generateToken();

        confirmationTokenService.save(
			ConfirmationToken.builder()
				.token(token)
				.user(user)
				.build()
		);
        
		try {
			mailSenderService.sendUserRegister(user,
					AppUtil.getRootUrlAppConcatPath("/confirmation-account/" + token)
			);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return user;
	}

    @Override
    @Transactional
    public User confirmUserAccount(String token) {
		ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);
		User user = confirmationToken.getUser();

		if (user.getEnabled()) {
			log.warn("Username {} already confirmed.", user.getUsername());
			throw new UsernameAlreadyConfirmedException();
		}

        userService.enableUser(user);
        confirmationTokenService.setConfirmedAt(token);

        return user;
    }

	@Override
	@Transactional
	public void resendConfirmation(User request) {
		log.info("Resend confirmation user {}", request.getUsername());

		User user = userService.findByUsernameOrThrowUserNotExistException(request.getUsername());

		Optional<ConfirmationToken> tokenFound = confirmationTokenService
				.findByUsernameNotExpiratedAndNotConfirmed(user.getUsername());

		if (tokenFound.isPresent()) {
			var minutes = Long.toString(Duration.between(LocalDateTime.now(), tokenFound.get().getExpiresAt()).plusMinutes(1L).toMinutes());
			throw new BadRequestException("Email already sent. Please wait " + minutes + " minutes for new request");
		}

		if (user.getEnabled()) {
			log.warn("Username {} already confirmed.", user.getUsername());
			throw new UsernameAlreadyConfirmedException();
		}

		String token = confirmationTokenService.generateToken();

		confirmationTokenService.save(
			ConfirmationToken.builder()
				.token(token)
				.user(user)
				.build()
		);

		try {
			mailSenderService.sendUserRegister(user,
				AppUtil.getRootUrlAppConcatPath("/confirmation-account/" + token)
			);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
