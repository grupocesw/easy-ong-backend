package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CountryRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 255, message = "Name must contain between 3 and 255 characters")
	private String name;
	
	@NotEmpty(message = "Abbreviation required")
	@Size(min = 3, max = 3, message = "Abbreviation must contain 3 characters")
	private String abbreviation;

}