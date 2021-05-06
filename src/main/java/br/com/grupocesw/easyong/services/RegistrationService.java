package br.com.grupocesw.easyong.services;

import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.User;

@Service
public interface RegistrationService {

	public Boolean register(User user);
	
	public Boolean confirmToken(String token);

	public Boolean resendConfirmation(String username);
}
