package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.grupocesw.easyong.entities.Address;
import br.com.grupocesw.easyong.entities.Ngo;
import lombok.Data;

@Data
public class NgoFullResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String cnpj;
	private String description;
	private Address address;	
	private Set<SocialCauseResponseDto> causes = new HashSet<>();
	private Set<ContactResponseDto> contacts = new HashSet<>();
	private Set<NgoMoreInformationResponseDto> moreInformations = new HashSet<>();
	private Set<PictureResponseDto> pictures = new HashSet<>();

	public NgoFullResponseDto(Ngo ngo) {
		id = ngo.getId();
		name = ngo.getName();
		cnpj = ngo.getCnpj();
		description = ngo.getDescription();
		address = ngo.getAddress();
		
		causes = ngo.getCauses().stream()
				.map(obj -> new SocialCauseResponseDto(obj))
				.collect(Collectors.toSet());
		
		contacts = ngo.getContacts().stream()
				.map(obj -> new ContactResponseDto(obj))
				.collect(Collectors.toSet());
		
		pictures = ngo.getPictures().stream()
				.map(obj -> new PictureResponseDto(obj))
				.collect(Collectors.toSet());
		
		moreInformations = ngo.getMoreInformations().stream()
				.map(obj -> new NgoMoreInformationResponseDto(obj))
				.collect(Collectors.toSet());
	}
}
