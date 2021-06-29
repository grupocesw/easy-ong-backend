package br.com.grupocesw.easyong.services;

import java.io.IOException;

import javax.mail.MessagingException;

import br.com.grupocesw.easyong.entities.User;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.response.dtos.UserResponseDto;

@Service
public interface EmailSenderService {
    
	void sendUserRegister(User user, String link) throws MessagingException, IOException;
}
