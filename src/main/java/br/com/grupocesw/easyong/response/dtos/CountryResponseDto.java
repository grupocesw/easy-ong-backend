package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.Country;
import lombok.Data;

@Data
public class CountryResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String abbreviation;

	public CountryResponseDto(Country country) {
		id = country.getId();
		name = country.getName();
		abbreviation = country.getAbbreviation();
	}
}
