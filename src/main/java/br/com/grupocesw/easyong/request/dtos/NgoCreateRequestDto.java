package br.com.grupocesw.easyong.request.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NgoCreateRequestDto implements Serializable {
	
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

	@Size(min = 1, message = "At least one picture required")
	@NotEmpty(message = "At least one cause required")
	private Set<Long> causeIds;

	@Size(min = 1, message = "At least one picture required")
	@NotEmpty(message = "At least one picture required")
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private Set<PictureRequestDto> pictures;

}