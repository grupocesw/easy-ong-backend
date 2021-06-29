package br.com.grupocesw.easyong.services;

import br.com.grupocesw.easyong.entities.User;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.request.dtos.UserCreateRequestDto;

@Service
public interface RegistrationService {

	Boolean register(User request);
	
	Boolean confirmToken(String token);

	Boolean resendConfirmation(String username);
}
