package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.grupocesw.easyong.entities.Country;
import lombok.Data;

@Data
public class StateRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 255, message = "Name must contain between 3 and 255 characters")
	private String name;
	
	@NotEmpty(message = "Abbreviation required")
	@Size(min = 2, max = 2, message = "Abbreviation must contain 2 characters")
	private String abbreviation;

	@NotEmpty(message = "Country required")
	private Country country;

}