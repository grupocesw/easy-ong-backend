package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class NgoRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="Name required")
	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
	private String name;

	@NotEmpty(message="CNPJ required")
	@Size(min = 14, max = 14, message = "CNPJ must contain 14 digits")
	private String cnpj;
	
	private String description;

	private Boolean activated = true;
	
	private AddressRequestDto address;
	
	@NotNull(message = "At least one contact required")
	private Set<ContactRequestDto> contacts;
	
	private Set<NgoMoreInformationRequestDto> moreInformations;
	
	@NotNull(message = "At least one cause required")
	private Set<Long> causeIds;
	
	@NotNull(message = "At least one picture required")
	private Set<PictureRequestDto> pictures;

}