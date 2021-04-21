package br.com.grupocesw.easyong.services;

import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.payloads.UserRequest;

@Service
public interface RegistrationService {

	public Boolean register(UserRequest request);
	
	public Boolean confirmToken(String token);
}
