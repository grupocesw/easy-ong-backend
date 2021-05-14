package br.com.grupocesw.easyong.services;

import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.request.dtos.UserCreateRequestDto;

@Service
public interface RegistrationService {

	public Boolean register(UserCreateRequestDto request);
	
	public Boolean confirmToken(String token);

	public Boolean resendConfirmation(String username);
}
