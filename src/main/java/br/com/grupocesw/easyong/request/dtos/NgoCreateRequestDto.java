package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.Picture;
import br.com.grupocesw.easyong.entities.SocialCause;
import lombok.Data;

@Data
public class NgoCreateRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="Name required")
	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
	private String name;
	
	@NotEmpty(message="CNPJ required")
	@Size(min = 14, max = 14, message = "CNPJ must contain 14 digits")
	private String cnpj;
	
	@NotEmpty(message="Description required")
	private String description;

	private Boolean activated = true;
	
	@NotNull(message = "Address required")
	private AddressCreateRequestDto address;
	
	@NotNull(message = "At least one contact required.")
	private Set<ContactCreateRequestDto> contacts;
	
	private Set<NgoMoreInformationCreateRequestDto> moreInformations;
	
	@NotNull(message = "At least one cause required")
	private Set<SocialCause> causes;
	
	@NotNull(message = "At least one picture required")
	private Set<Picture> pictures;
    
    public Ngo build() {
    	
    	return Ngo.builder()
    		.name(name)
    		.cnpj(cnpj)
    		.description(description)
    		.activated(activated)
    		.address(address.build())
    		.contacts(contacts.stream()
    				.map(obj -> obj.build()).collect(Collectors.toSet()))
    		.moreInformations(moreInformations.stream()
    				.map(obj -> obj.build()).collect(Collectors.toSet()))
    		.causes(causes)
    		.pictures(pictures)
			.build();
    }

}