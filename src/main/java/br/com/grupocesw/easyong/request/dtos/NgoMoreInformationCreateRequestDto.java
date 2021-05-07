package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.grupocesw.easyong.entities.NgoMoreInformation;
import lombok.Data;

@Data
public class NgoMoreInformationCreateRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Information required")
	@Size(min = 3, max = 255, message = "Information must contain between 3 and 255 characters")
	private String information;

    public NgoMoreInformation build() {
    	return NgoMoreInformation.builder()
    		.information(information)
			.build();
    }

}