package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.NgoMoreInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NgoMoreInformationResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String information;

	public NgoMoreInformationResponseDto(NgoMoreInformation moreInformation) {
		id = moreInformation.getId();
		information = moreInformation.getInformation();
	}
}
