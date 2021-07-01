package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NgoRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
	private String name;

	@NotEmpty(message = "CNPJ required")
	@Size(min = 14, max = 14, message = "CNPJ must contain 14 digits")
	private String cnpj;
	
	private String description;

	private Boolean activated = true;
	
	private AddressRequestDto address;
	
	@NotNull(message = "At least one contact required")
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private Set<ContactRequestDto> contacts;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private Set<NgoMoreInformationRequestDto> moreInformations;
	
	@NotNull(message = "At least one cause required")
	@Size(min = 1, message = "At least one picture required")
	private Set<Long> causeIds;

	@Size(min = 1, message = "At least one picture required")
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private Set<PictureRequestDto> pictures;

}