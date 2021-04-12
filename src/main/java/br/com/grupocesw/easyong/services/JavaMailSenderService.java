package br.com.grupocesw.easyong.services;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.grupocesw.easyong.entities.User;

@Service
public class JavaMailSenderService {
	
	@Autowired
	private JavaMailSender javaMailSender;
    
	@Async
	public void sendUserRegister(User user) throws MessagingException, IOException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    
		String url = ServletUriComponentsBuilder
			.fromCurrentContextPath()
			.build()
			.toUriString()
			.concat("/api/auth/verify/" + user.getUsername() + "/")
			.concat(user.getVerificationCode());
		
		helper.setFrom("noreply@easyong.com.br");
		helper.setTo(user.getUsername());
		helper.setSubject("Easy ONG - Confirmação de E-mail");
		
		String text = new String(""
				+ "<html>"
				+ "<font color='red'>Aplicativo Easy ONG</font>"
				+ "<p>Olá " + user.getName() + ", seja bem vindo.</p>"
				+ "<p>Para confirmar seu cadastro clique no link: " + url + "</p>"
				+ "</html>");
		
		helper.setText(text, true);
		
		javaMailSender.send(message);
	}    
}
