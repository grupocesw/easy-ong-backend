package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

}
