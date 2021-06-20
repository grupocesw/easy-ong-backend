package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.User;
import lombok.Data;

@Data
public class NgoResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String cnpj;
	private String description;
	private boolean favorited;
	private AddressResponseDto address;
	private Set<SocialCauseResponseDto> causes;
	private Set<ContactResponseDto> contacts;
	private Set<NgoMoreInformationResponseDto> moreInformations;
	private Set<PictureResponseDto> pictures;

	public NgoResponseDto(Ngo ngo) {
		id = ngo.getId();
		name = ngo.getName();
		cnpj = ngo.getCnpj();
		description = ngo.getDescription();
		address = new AddressResponseDto(ngo.getAddress());

		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			
			User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (ngo.getUsers() != null) {
				for (User user: ngo.getUsers()) {
					if (authenticatedUser.getUsername().equals(user.getUsername()))
						favorited = true;
				}
			}
		}

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
