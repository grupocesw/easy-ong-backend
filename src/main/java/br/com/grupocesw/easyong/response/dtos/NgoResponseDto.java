package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import br.com.grupocesw.easyong.entities.Address;
import br.com.grupocesw.easyong.entities.Contact;
import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.NgoMoreInformation;
import br.com.grupocesw.easyong.entities.Picture;
import br.com.grupocesw.easyong.entities.SocialCause;
import lombok.Data;

@Data
public class NgoResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String cnpj;
	private String description;
	private Address address;	
	private Set<SocialCause> causes;
	private Set<Contact> contacts;
	private Set<NgoMoreInformation> moreInformations;
	private Set<PictureResponseDto> pictures = new HashSet<>();

	public NgoResponseDto(Ngo ngo) {
		id = ngo.getId();
		name = ngo.getName();
		cnpj = ngo.getCnpj();
		description = ngo.getDescription();
		address = ngo.getAddress();
		causes = ngo.getCauses();
		contacts = ngo.getContacts();
		moreInformations = ngo.getMoreInformations();

		for (Picture p: ngo.getPictures()) {
			pictures.add(new PictureResponseDto(p));
	    }
	}
}
