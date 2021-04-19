package br.com.grupocesw.easyong.services;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.User;

@Service
public interface JavaMailSenderService {
    
	public void sendUserRegister(User user) throws MessagingException, IOException;  
}
