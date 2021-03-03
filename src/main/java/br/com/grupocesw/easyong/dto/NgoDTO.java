package br.com.grupocesw.easyong.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import br.com.grupocesw.easyong.entities.Address;
import br.com.grupocesw.easyong.entities.Contact;
import br.com.grupocesw.easyong.entities.MoreInformationNgo;
import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.Picture;
import br.com.grupocesw.easyong.entities.SocialCause;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NgoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String description;
	private Address address;
	private Set<SocialCause> causes;
	private Set<Contact> contacts;
	private Set<MoreInformationNgo> moreInformations;
	private Set<PictureDTO> pictures = new HashSet<>();

	public NgoDTO(Ngo ngo) {
		id = ngo.getId();
		name = ngo.getName();
		description = ngo.getDescription();
		address = ngo.getAddress();
		causes = ngo.getCauses();
		contacts = ngo.getContacts();
		moreInformations = ngo.getMoreInformations();

		for (Picture p: ngo.getPictures()) {
			pictures.add(new PictureDTO(p));
	    }
	}
}
