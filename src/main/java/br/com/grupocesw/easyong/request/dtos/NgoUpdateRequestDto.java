package br.com.grupocesw.easyong.request.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NgoUpdateRequestDto implements Serializable {
	
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

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private Set<ContactRequestDto> contacts;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private Set<NgoMoreInformationRequestDto> moreInformations;

	private Set<Long> causeIds;

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private Set<PictureRequestDto> pictures;

}