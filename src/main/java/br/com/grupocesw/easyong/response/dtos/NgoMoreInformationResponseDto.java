package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.NgoMoreInformation;
import lombok.Data;

@Data
public class NgoMoreInformationResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String information;

	public NgoMoreInformationResponseDto(NgoMoreInformation moreInformation) {
		id = moreInformation.getId();
		information = moreInformation.getInformation();
	}
}
