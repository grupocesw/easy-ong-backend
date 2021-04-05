package br.com.grupocesw.easyong.services;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.grupocesw.easyong.entities.User;

@Service
public class JavaMailSenderService {
	
    @Autowired
    private JavaMailSender javaMailSender;
    
    public void send() {
        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo("dreeh.silva@hotmail.com", "to_2@gmail.com", "to_3@yahoo.com");
        message.setTo("dreeh.silva@hotmail.com");
        message.setSubject("Testing from Spring Boot");
        message.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(message);
    }
    
   public void sendUserRegister(User user) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        String url = ServletUriComponentsBuilder
        		.fromCurrentContextPath()
        		.build()
        		.toUriString()
        		.concat("/api/auth/verify/" + user.getUsername() + "/")
        		.concat(user.getPassword().replaceAll("\\D+", ""));
        
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
    
   public void sendEmailWithAttachment() throws MessagingException, IOException {

    	MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        
        helper.setTo("to_@email");

        helper.setSubject("Testing from Spring Boot");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Check attachment for image!</h1>", true);

        // hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));

        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);

    }
}
