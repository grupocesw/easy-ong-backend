package br.com.grupocesw.easyong.configurations;
//package br.com.grupocesw.easyong.configurations;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import br.com.grupocesw.easyong.entities.Address;
//import br.com.grupocesw.easyong.entities.City;
//import br.com.grupocesw.easyong.entities.Contact;
//import br.com.grupocesw.easyong.entities.Country;
//import br.com.grupocesw.easyong.entities.District;
//import br.com.grupocesw.easyong.entities.FrequentlyAskedQuestion;
//import br.com.grupocesw.easyong.entities.MoreInformationNog;
//import br.com.grupocesw.easyong.entities.Ngo;
//import br.com.grupocesw.easyong.entities.Picture;
//import br.com.grupocesw.easyong.entities.SocialCause;
//import br.com.grupocesw.easyong.entities.State;
//import br.com.grupocesw.easyong.entities.Street;
//import br.com.grupocesw.easyong.entities.User;
//import br.com.grupocesw.easyong.entities.ZipCode;
//import br.com.grupocesw.easyong.enumerations.ContactType;
//import br.com.grupocesw.easyong.enumerations.Gender;
//import br.com.grupocesw.easyong.repositories.CityRepository;
//import br.com.grupocesw.easyong.repositories.ContactRepository;
//import br.com.grupocesw.easyong.repositories.CountryRepository;
//import br.com.grupocesw.easyong.repositories.DistrictRepository;
//import br.com.grupocesw.easyong.repositories.FrequentlyAskedQuestionRepository;
//import br.com.grupocesw.easyong.repositories.MoreInformationNogRepository;
//import br.com.grupocesw.easyong.repositories.NgoRepository;
//import br.com.grupocesw.easyong.repositories.PictureRepository;
//import br.com.grupocesw.easyong.repositories.SocialCauseRepository;
//import br.com.grupocesw.easyong.repositories.StateRepository;
//import br.com.grupocesw.easyong.repositories.StreetRepository;
//import br.com.grupocesw.easyong.repositories.UserRepository;
//import br.com.grupocesw.easyong.repositories.ZipCodeRepository;
//
//@Configuration
//@Profile("dev")
//public class DevConfiguration implements CommandLineRunner {
//
//	@Autowired
//	private FrequentlyAskedQuestionRepository faqr;
//
//	@Autowired
//	private UserRepository ur;
//
//	@Autowired
//	private SocialCauseRepository scr;
//
//	@Autowired
//	private CountryRepository cr;
//
//	@Autowired
//	private StateRepository sr;
//
//	@Autowired
//	private CityRepository ctr;
//
//	@Autowired
//	private DistrictRepository dr;
//
//	@Autowired
//	private ZipCodeRepository zcr;
//
//	@Autowired
//	private StreetRepository str;
//
//	@Autowired
//	private NgoRepository nr;
//
//	@Autowired
//	private ContactRepository cor;
//
//	@Autowired
//	private MoreInformationNogRepository mir;
//
//	@Autowired
//	private PictureRepository picr;
//
//	@Override
//	public void run(String... args) throws Exception {
//		List<FrequentlyAskedQuestion> faqs = new ArrayList<>();
//		faqs.addAll(Arrays.asList(
//				new FrequentlyAskedQuestion(null, "Vocês tem vinculos com alguma ONG?", "Não. Somos totalmente independentes."),
//				new FrequentlyAskedQuestion(null, "O app é gratuíto?", "Sim. Ele é totalmente gratuíto."),
//				new FrequentlyAskedQuestion(null, "Tem opção para IOS?", "Somente para plataforma android inicialmente.")
//		));
//		faqr.saveAll(faqs);
//		List<SocialCause> socialCauses = new ArrayList<>();
//		socialCauses.addAll(Arrays.asList(new SocialCause(null, "Educação"), new SocialCause(null, "Proteção Animal"),
//				new SocialCause(null, "Direitos Humanos"), new SocialCause(null, "Esporte e Cultura"),
//				new SocialCause(null, "Meio Ambiente"), new SocialCause(null, "Luta Contra Fome"),
//				new SocialCause(null, "Saúde e Bem Estar"), new SocialCause(null, "Direitos Humanos")));
//		scr.saveAll(socialCauses);
//
//		List<Country> countries = new ArrayList<>();
//		countries.addAll(Arrays.asList(new Country(null, "Brasil", "BRA")));
//		cr.saveAll(countries);
//
//		List<State> states = new ArrayList<>();
//		states.addAll(Arrays.asList(new State(null, "Acre", "AC", countries.get(0)),
//				new State(null, "Alagoas", "AL", countries.get(0)), new State(null, "Amapá", "AP", countries.get(0)),
//				new State(null, "Amazonas", "AM", countries.get(0)), new State(null, "Bahia", "BA", countries.get(0)),
//				new State(null, "Ceará", "CE", countries.get(0)),
//				new State(null, "Espírito Santo", "ES", countries.get(0)),
//				new State(null, "Goiás", "GO", countries.get(0)), new State(null, "Maranhão", "MA", countries.get(0)),
//				new State(null, "Mato Grosso", "MT", countries.get(0)),
//				new State(null, "Mato Grosso do Sul", "MS", countries.get(0)),
//				new State(null, "Minas Gerais", "MG", countries.get(0)),
//				new State(null, "Pará", "PA", countries.get(0)), new State(null, "Paraíba", "PB", countries.get(0)),
//				new State(null, "Paraná", "PR", countries.get(0)),
//				new State(null, "Pernambuco", "PE", countries.get(0)), new State(null, "Piauí", "PI", countries.get(0)),
//				new State(null, "Rio de Janeiro", "RJ", countries.get(0)),
//				new State(null, "Rio Grande do Norte", "RN", countries.get(0)),
//				new State(null, "Rio Grande do Sul", "RS", countries.get(0)),
//				new State(null, "Rondônia", "RO", countries.get(0)), new State(null, "Roraima", "RR", countries.get(0)),
//				new State(null, "Santa Catarina", "SC", countries.get(0)),
//				new State(null, "São Paulo", "SP", countries.get(0)),
//				new State(null, "Sergipe", "SE", countries.get(0)),
//				new State(null, "Tocantins", "TO", countries.get(0)),
//				new State(null, "Distrito Federal", "DF", countries.get(0))));
//		sr.saveAll(states);
//
//		List<City> cities = new ArrayList<>();
//		cities.addAll(Arrays.asList(new City(null, "Belo Horizonte", states.get(11)),
//				new City(null, "São Paulo", states.get(24)), new City(null, "Rio de Janeiro", states.get(17))));
//		ctr.saveAll(cities);
//
//		List<ZipCode> zipCodes = new ArrayList<>();
//		zipCodes.addAll(Arrays.asList(new ZipCode(null, "01227000"), new ZipCode(null, "01304001"),
//				new ZipCode(null, "01156050")));
//		zcr.saveAll(zipCodes);
//
//		List<District> districts = new ArrayList<>();
//		districts.addAll(Arrays.asList(new District(null, "Santa Cecília", cities.get(1)),
//				new District(null, "Consolação", cities.get(1)), new District(null, "Barra Funda", cities.get(1))));
//		dr.saveAll(districts);
//
//		List<Street> streets = new ArrayList<>();
//		streets.addAll(Arrays.asList(new Street(null, "Avenida Angélica", zipCodes.get(0), districts.get(0)),
//				new Street(null, "Rua Augusta", zipCodes.get(1), districts.get(1)),
//				new Street(null, "Av. Dr. Adolpho Pinto", zipCodes.get(2), districts.get(2))));
//		str.saveAll(streets);
//
//		List<Address> addresses = new ArrayList<>();
//		addresses.addAll(Arrays.asList(new Address(null, 19, streets.get(0)), new Address(null, 365, streets.get(1)),
//				new Address(null, 876, streets.get(2))));
//
//		List<Picture> ngoPictures = new ArrayList<>();
//		ngoPictures.addAll(Arrays.asList(new Picture(null, "1_ong.jpeg"), new Picture(null, "2_ong.png"),
//				new Picture(null, "3_ong.jpg")));
//		picr.saveAll(ngoPictures);
//
//		addresses.get(0).setComplement("Bloco C APTO 22");
//		addresses.get(0).setLatitude("37.4267861");
//		addresses.get(0).setLongitude("-122.0806032");
//
//		List<Ngo> ngos = new ArrayList<>();
//		ngos.addAll(Arrays.asList(new Ngo(null, "Ong Cantinho Feliz", "59300268000191", addresses.get(0),
//				"Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero."),
//				new Ngo(null, "Ong Bom de Bola", "26809488000196", addresses.get(1),
//						"Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero."),
//				new Ngo(null, "Ong Green Side", "52851775000174", addresses.get(2),
//						"Lorem ipsum hendrerit ut arcu dapibus etiam habitant faucibus gravida egestas, ut porttitor blandit venenatis per vestibulum venenatis massa leo quisque, pretium mattis auctor lorem curabitur ut aliquet praesent libero.")));
//
//		ngos.get(0).getCauses().add(socialCauses.get(1));
//		ngos.get(0).getCauses().add(socialCauses.get(7));
//		ngos.get(1).getCauses().add(socialCauses.get(5));
//		ngos.get(2).getCauses().add(socialCauses.get(1));
//		ngos.get(2).getCauses().add(socialCauses.get(6));
//		ngos.get(0).getCauses().add(socialCauses.get(4));
//
//		ngos.get(0).getPictures().addAll(ngoPictures);
//		nr.saveAll(ngos);
//
//		List<Contact> contacts = new ArrayList<>();
//		contacts.addAll(Arrays.asList(new Contact(null, ContactType.WHATSAPP, "011954563652"),
//				new Contact(null, ContactType.EMAIL, "cantinho.feliz@gmail.com"),
//				new Contact(null, ContactType.WEB_SITE, "https://cantinhofeliz.com.br"),
//				new Contact(null, ContactType.PHONE, "0112223333"),
//				new Contact(null, ContactType.TWITTER, "@cantinhofeliz2020")));
//
//		contacts.get(0).setNgo(ngos.get(0));
//		contacts.get(1).setNgo(ngos.get(0));
//		contacts.get(2).setNgo(ngos.get(0));
//		contacts.get(3).setNgo(ngos.get(0));
//		contacts.get(4).setNgo(ngos.get(0));
//		cor.saveAll(contacts);
//
//		List<MoreInformationNog> informations = new ArrayList<>();
//		informations
//				.addAll(Arrays.asList(new MoreInformationNog(null, "No ano de 2020 foram doadas 800 cestas básicas."),
//						new MoreInformationNog(null, "Somos mais de 40 voluntários."),
//						new MoreInformationNog(null, "Foram arrecadados mais de R$50000 em 2020.")));
//
//		informations.get(0).setNgo(ngos.get(0));
//		informations.get(1).setNgo(ngos.get(0));
//		informations.get(2).setNgo(ngos.get(1));
//		mir.saveAll(informations);
//
//		List<User> users = new ArrayList<>();
//		users.addAll(Arrays.asList(
//				new User(null, "Astrobilobaldo Paixão", LocalDate.parse("1990-09-22"), Gender.MALE,
//						"astrobilobaldo@test.com", "123456"),
//				new User(null, "Biri Jean", LocalDate.parse("1983-07-23"), Gender.MALE, "birijean@easyong.com.br",
//						"123456"),
//				new User(null, "Paul Stronger", LocalDate.parse("1998-05-04"), Gender.MALE,
//						"paulstronger@easyong.com.br", "123456"),
//				new User(null, "Sander Jay", LocalDate.parse("1996-11-29"), Gender.MALE, "sanderjay@easyong.com.br",
//						"123456"),
//				new User(null, "Juma Haskov", LocalDate.parse("2000-06-07"), Gender.FEMALE, "jumahaskov@easyong.com.br",
//						"123456"),
//				new User(null, "Mirna Kyle", LocalDate.parse("1967-03-09"), Gender.FEMALE, "mirnakyle@easyong.com.br",
//						"123456"),
//				new User(null, "Tricia Marcos", LocalDate.parse("1984-12-25"), Gender.OTHER,
//						"triciamarcos@easyong.com.br", "123456")));
//
//		users.get(0).getCauses().add(socialCauses.get(7));
//		users.get(0).getCauses().add(socialCauses.get(3));
//		users.get(2).getCauses().add(socialCauses.get(4));
//		users.get(5).getCauses().add(socialCauses.get(6));
//		
//		users.get(0).setPicture(new Picture(null, "4_user.jpg", users.get(0)));
//		ur.saveAll(users);
//	}
//
//}
