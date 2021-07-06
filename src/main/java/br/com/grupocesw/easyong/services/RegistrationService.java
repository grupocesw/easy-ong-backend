package br.com.grupocesw.easyong.services;

import br.com.grupocesw.easyong.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {

	User register(User request);

	User confirmUserAccount(String token);

	void resendConfirmation(User request);
}
