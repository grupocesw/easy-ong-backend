package br.com.grupocesw.easyong.configurations;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.enumerations.Gender;
import br.com.grupocesw.easyong.repositories.SocialCauseRepository;
import br.com.grupocesw.easyong.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfiguration implements CommandLineRunner {

	@Autowired
	private UserRepository ur;
	
	@Autowired
	private SocialCauseRepository scr;

	@Override
	public void run(String... args) throws Exception {		
		SocialCause sc1 = new SocialCause(null, "Educação");
		SocialCause sc2 = new SocialCause(null, "Proteção Animal");
		SocialCause sc3 = new SocialCause(null, "Direitos Humanos");
		SocialCause sc4 = new SocialCause(null, "Esporte e Cultura");
		SocialCause sc5 = new SocialCause(null, "Meio Ambiente");
		SocialCause sc6 = new SocialCause(null, "Luta Contra Fome");
		SocialCause sc7 = new SocialCause(null, "Saúde e Bem Estar");
		SocialCause sc8 = new SocialCause(null, "Direitos Humanos");
		scr.saveAll(Arrays.asList(sc1, sc2, sc3, sc4, sc5, sc6, sc7, sc8));
		
		User u1 = new User(null, "Astrobilobaldo Paixão", LocalDate.parse("1990-09-22"), Gender.MALE, "astrobilobaldo@test.com", "123456");
		User u2 = new User(null, "Biri Jean", LocalDate.parse("1983-07-23"), Gender.MALE, "birijean@easyong.com.br", "123456");
		User u3 = new User(null, "Paul Stronger", LocalDate.parse("1998-05-04"), Gender.MALE, "paulstronger@easyong.com.br", "123456");
		User u4 = new User(null, "Sander Jay", LocalDate.parse("1996-11-29"), Gender.MALE, "sanderjay@easyong.com.br", "123456");
		User u5 = new User(null, "Juma Haskov", LocalDate.parse("2000-06-07"), Gender.FEMALE, "jumahaskov@easyong.com.br", "123456");
		User u6 = new User(null, "Mirna Kyle", LocalDate.parse("1967-03-09"), Gender.FEMALE, "mirnakyle@easyong.com.br", "123456");
		User u7 = new User(null, "Tricia Marcos", LocalDate.parse("1984-12-25"), Gender.OTHER, "triciamarcos@easyong.com.br", "123456");		
		
		u1.getCauses().add(sc8);
		u1.getCauses().add(sc4);
		u3.getCauses().add(sc5);
		u6.getCauses().add(sc7);
		
		ur.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6, u7));
	}

}
