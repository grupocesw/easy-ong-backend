package br.com.grupocesw.easyong.services;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.response.dtos.UserResponseDto;

@Service
public interface EmailSenderService {
    
	void sendUserRegister(UserResponseDto user, String link) throws MessagingException, IOException;
}
