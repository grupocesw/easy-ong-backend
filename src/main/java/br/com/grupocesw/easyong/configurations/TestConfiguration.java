package br.com.grupocesw.easyong.configurations;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.grupocesw.easyong.entities.Address;
import br.com.grupocesw.easyong.entities.City;
import br.com.grupocesw.easyong.entities.Country;
import br.com.grupocesw.easyong.entities.District;
import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.entities.State;
import br.com.grupocesw.easyong.entities.Street;
import br.com.grupocesw.easyong.entities.StreetZipCodeDistrict;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.entities.ZipCode;
import br.com.grupocesw.easyong.enumerations.Gender;
import br.com.grupocesw.easyong.repositories.CityRepository;
import br.com.grupocesw.easyong.repositories.CountryRepository;
import br.com.grupocesw.easyong.repositories.DistrictRepository;
import br.com.grupocesw.easyong.repositories.NgoRepository;
import br.com.grupocesw.easyong.repositories.SocialCauseRepository;
import br.com.grupocesw.easyong.repositories.StateRepository;
import br.com.grupocesw.easyong.repositories.StreetRepository;
import br.com.grupocesw.easyong.repositories.StreetZipCodeDistrictRepository;
import br.com.grupocesw.easyong.repositories.UserRepository;
import br.com.grupocesw.easyong.repositories.ZipCodeRepository;

@Configuration
@Profile("test")
public class TestConfiguration implements CommandLineRunner {

	@Autowired
	private UserRepository ur;

	@Autowired
	private SocialCauseRepository scr;

	@Autowired
	private CountryRepository cr;

	@Autowired
	private StateRepository sr;

	@Autowired
	private CityRepository ctr;

	@Autowired
	private DistrictRepository dr;

	@Autowired
	private ZipCodeRepository zcr;

	@Autowired
	private StreetRepository str;

	@Autowired
	private StreetZipCodeDistrictRepository szdr;

	@Autowired
	private NgoRepository nr;

	@Override
	public void run(String... args) throws Exception {

		Country c1 = new Country(null, "Brasil", "BRA");
		cr.save(c1);

		State s1 = new State(null, "Acre", "AC", c1);
		State s2 = new State(null, "Alagoas", "AL", c1);
		State s3 = new State(null, "Amapá", "AP", c1);
		State s4 = new State(null, "Amazonas", "AM", c1);
		State s5 = new State(null, "Bahia", "BA", c1);
		State s6 = new State(null, "Ceará", "CE", c1);
		State s7 = new State(null, "Espírito Santo", "ES", c1);
		State s8 = new State(null, "Goiás", "GO", c1);
		State s9 = new State(null, "Maranhão", "MA", c1);
		State s10 = new State(null, "Mato Grosso", "MT", c1);
		State s11 = new State(null, "Mato Grosso do Sul", "MS", c1);
		State s12 = new State(null, "Minas Gerais", "MG", c1);
		State s13 = new State(null, "Pará", "PA", c1);
		State s14 = new State(null, "Paraíba", "PB", c1);
		State s15 = new State(null, "Paraná", "PR", c1);
		State s16 = new State(null, "Pernambuco", "PE", c1);
		State s17 = new State(null, "Piauí", "PI", c1);
		State s18 = new State(null, "Rio de Janeiro", "RJ", c1);
		State s19 = new State(null, "Rio Grande do Norte", "RN", c1);
		State s20 = new State(null, "Rio Grande do Sul", "RS", c1);
		State s21 = new State(null, "Rondônia", "RO", c1);
		State s22 = new State(null, "Roraima", "RR", c1);
		State s23 = new State(null, "Santa Catarina", "SC", c1);
		State s24 = new State(null, "São Paulo", "SP", c1);
		State s25 = new State(null, "Sergipe", "SE", c1);
		State s26 = new State(null, "Tocantins", "TO", c1);
		State s27 = new State(null, "Distrito Federal", "DF", c1);
		sr.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19,
				s20, s21, s22, s23, s24, s25, s26, s27));

		City ct1 = new City(null, "Belo Horizonte", s12);
		City ct2 = new City(null, "São Paulo", s24);
		City ct3 = new City(null, "Rio de Janeiro", s18);
		ctr.saveAll(Arrays.asList(ct1, ct2, ct3));

		Street st1 = new Street(null, "Avenida Angélica");
		Street st2 = new Street(null, "Rua Augusta");
		Street st3 = new Street(null, "Av. Dr. Adolpho Pinto");
		str.saveAll(Arrays.asList(st1, st2, st3));

		ZipCode zp1 = new ZipCode(null, "01227000");
		ZipCode zp2 = new ZipCode(null, "01304001");
		ZipCode zp3 = new ZipCode(null, "01156050");
		zcr.saveAll(Arrays.asList(zp1, zp2, zp3));

		District d1 = new District(null, "Santa Cecília", ct1);
		District d2 = new District(null, "Consolação", ct1);
		District d3 = new District(null, "Barra Funda", ct1);

		dr.saveAll(Arrays.asList(d1, d2, d3));

		StreetZipCodeDistrict szc1 = new StreetZipCodeDistrict();
		szc1.getStreets().add(st1);
		szc1.getZipCodes().add(zp1);
		szc1.getDistricts().add(d1);

		StreetZipCodeDistrict szc2 = new StreetZipCodeDistrict();
		szc2.getStreets().add(st2);
		szc2.getZipCodes().add(zp2);
		szc2.getDistricts().add(d2);

		StreetZipCodeDistrict szc3 = new StreetZipCodeDistrict();
		szc3.getStreets().add(st3);
		szc3.getZipCodes().add(zp3);
		szc3.getDistricts().add(d3);

		szdr.saveAll(Arrays.asList(szc1, szc2, szc3));

		Address a1 = new Address(null, 19, szc1);
		a1.setComplement("Bloco C APTO 22");
		a1.setLatitude("37.4267861");
		a1.setLongitude("-122.0806032");
		Address a2 = new Address(null, 365, szc2);
		Address a3 = new Address(null, 876, szc3);

		SocialCause sc1 = new SocialCause(null, "Educação");
		SocialCause sc2 = new SocialCause(null, "Proteção Animal");
		SocialCause sc3 = new SocialCause(null, "Direitos Humanos");
		SocialCause sc4 = new SocialCause(null, "Esporte e Cultura");
		SocialCause sc5 = new SocialCause(null, "Meio Ambiente");
		SocialCause sc6 = new SocialCause(null, "Luta Contra Fome");
		SocialCause sc7 = new SocialCause(null, "Saúde e Bem Estar");
		SocialCause sc8 = new SocialCause(null, "Direitos Humanos");
		scr.saveAll(Arrays.asList(sc1, sc2, sc3, sc4, sc5, sc6, sc7, sc8));

		Ngo n1 = new Ngo(null, "Ong Cantinho Feliz", "59300268000191", a1,
				"Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero.");
		Ngo n2 = new Ngo(null, "Ong Bom de Boa", "26809488000196", a2,
				"Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero.");
		Ngo n3 = new Ngo(null, "Ong Green Side", "52851775000174", a3,
				"Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero.");
		n1.getCauses().add(sc2);
		n1.getCauses().add(sc8);
		n2.getCauses().add(sc6);
		n3.getCauses().add(sc2);
		n3.getCauses().add(sc7);
		n3.getCauses().add(sc5);
		nr.saveAll(Arrays.asList(n1, n2, n3));

		User u1 = new User(null, "Astrobilobaldo Paixão", LocalDate.parse("1990-09-22"), Gender.MALE,
				"astrobilobaldo@test.com", "123456");
		User u2 = new User(null, "Biri Jean", LocalDate.parse("1983-07-23"), Gender.MALE, "birijean@easyong.com.br",
				"123456");
		User u3 = new User(null, "Paul Stronger", LocalDate.parse("1998-05-04"), Gender.MALE,
				"paulstronger@easyong.com.br", "123456");
		User u4 = new User(null, "Sander Jay", LocalDate.parse("1996-11-29"), Gender.MALE, "sanderjay@easyong.com.br",
				"123456");
		User u5 = new User(null, "Juma Haskov", LocalDate.parse("2000-06-07"), Gender.FEMALE,
				"jumahaskov@easyong.com.br", "123456");
		User u6 = new User(null, "Mirna Kyle", LocalDate.parse("1967-03-09"), Gender.FEMALE, "mirnakyle@easyong.com.br",
				"123456");
		User u7 = new User(null, "Tricia Marcos", LocalDate.parse("1984-12-25"), Gender.OTHER,
				"triciamarcos@easyong.com.br", "123456");

		u1.getCauses().add(sc8);
		u1.getCauses().add(sc4);
		u3.getCauses().add(sc5);
		u6.getCauses().add(sc7);

		ur.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6, u7));
	}

}
