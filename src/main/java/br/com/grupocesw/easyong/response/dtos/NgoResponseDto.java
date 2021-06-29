package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;
import java.util.Set;

import br.com.grupocesw.easyong.mappers.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.User;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
		address = AddressMapper.INSTANCE.entityToResponseDto(ngo.getAddress());
		causes = SocialCauseMapper.INSTANCE.listToResponseDtoSet(ngo.getCauses());
		contacts = ContactMapper.INSTANCE.listToResponseDtoSet(ngo.getContacts());
		moreInformations = NgoMoreInformationMapper.INSTANCE.listToResponseDtoSet(ngo.getMoreInformations());
		pictures = PictureMapper.INSTANCE.listToResponseDtoSet(ngo.getPictures());

		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {

			User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (ngo.getUsers() != null) {
				for (User user: ngo.getUsers()) {
					if (authenticatedUser.getUsername().equals(user.getUsername()))
						favorited = true;
				}
			}
		}

	}
}
